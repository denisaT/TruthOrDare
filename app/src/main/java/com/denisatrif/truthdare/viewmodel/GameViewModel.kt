package com.denisatrif.truthdare.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.denisatrif.truthdare.billing.InAppConstants
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.PlayersRepository
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val truthDaresRepository: TruthDareRepository,
    private val playersRepository: PlayersRepository
) : ViewModel() {

    private val TAG: String = "GameViewModel"
    var players = mutableListOf<Player>()
    var currentPlayerPosition = 0
    var dirtyModeCounter = 0
    var sexyModeCounter = 0
    var partyModeTruthCounter = 0
    var partyModeDareCounter = 0
    var currentPurchases: List<Purchase?> = mutableListOf()
    private lateinit var billingClient: BillingClient
    private var queried: Boolean = false
    val skuDetailsMap: HashMap<String, SkuDetails> = HashMap()

    private fun nextPlayer() {
        if (currentPlayerPosition < players.size - 1) {
            currentPlayerPosition++
        } else {
            currentPlayerPosition = 0
        }
    }

    fun getAllPlayers(): LiveData<List<Player>> {
        val liveData = MutableLiveData<List<Player>>()
        viewModelScope.launch(Dispatchers.IO) {
            val players = playersRepository.getAllPlayers()
            liveData.postValue(players)
        }
        return liveData
    }

    fun getCurrentPlayer() = players[currentPlayerPosition]

    fun getRandomTruth(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = if (isTypePurchased(type)) {
                truthDaresRepository.getRandomTruth(type)
            } else {
                truthDaresRepository.getRandomLiteTruth(type)
            }
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getRandomDare(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomDare = if (isTypePurchased(type)) {
                truthDaresRepository.getRandomDare(type)
            } else {
                truthDaresRepository.getRandomLiteDare(type)
            }
            liveData.postValue(randomDare)
        }
        return liveData
    }

    fun getNextPlayer(): Player {
        nextPlayer()
        return players[currentPlayerPosition]
    }

    fun querySkuDetails(
        billingClient: BillingClient,
    ) {
        val skuList = ArrayList<String>()
        skuList.add(InAppConstants.IN_APP_SEXY_PACK_PRODUCT_ID)
        skuList.add(InAppConstants.IN_APP_DIRTY_PACK_PRODUCT_ID)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
            Log.d(TAG, "onSkuDetailsResponse ${billingResult.responseCode}")
            Log.d(TAG, "Existing skus: $skuDetailsList")
            if (skuDetailsList != null) {
                for (skuDetail in skuDetailsList) {
                    skuDetailsMap[skuDetail.sku] = skuDetail
                    Log.i(TAG, skuDetail.toString())
                }
            } else {
                Log.i(TAG, "No skus found from query")
            }
        }
    }

    private fun queryPurchases(billingClient: BillingClient) {
        Log.d(TAG, "queryPurchases: Query for purchases")
        if (!billingClient.isReady) {
            Log.e(TAG, "queryPurchases: BillingClient is not ready")
            return
        }
        billingClient.queryPurchasesAsync(
            BillingClient.SkuType.INAPP
        ) { _, purchasesList ->
            run {
                currentPurchases = purchasesList
                queried = true
                Log.i(TAG, "Existing purchases: $purchasesList")
            }
        }
    }

    fun incrementCounter(questionType: QuestionType) {
        if (!queried) {
            queryPurchases(billingClient)
            querySkuDetails(billingClient)
        }
        if (isTypePurchased(questionType)) {
            when (questionType) {
                QuestionType.DIRTY -> dirtyModeCounter = 0
                QuestionType.SEXY -> sexyModeCounter = 0
                else -> {}
            }
        } else {
            when (questionType) {
                QuestionType.DIRTY -> dirtyModeCounter++
                QuestionType.SEXY -> sexyModeCounter++
                else -> {}
            }
        }
    }

    private fun isTypePurchased(questionType: QuestionType): Boolean {
        for (purchase in currentPurchases) {
            for (sku in purchase!!.skus) {
                when (questionType) {
                    QuestionType.DIRTY ->
                        if (sku.contains(InAppConstants.IN_APP_DIRTY_PACK_PRODUCT_ID))
                            return true
                    QuestionType.SEXY -> if (sku.contains(InAppConstants.IN_APP_SEXY_PACK_PRODUCT_ID))
                        return true
                    else -> {}
                }
            }
        }
        return false
    }

    fun isLimitReachedForType(questionType: QuestionType): Boolean {
        return when (questionType) {
            QuestionType.DIRTY -> {
                dirtyModeCounter >= 5
            }
            QuestionType.SEXY -> {
                sexyModeCounter >= 5
            }
            else -> {
                false
            }
        }
    }

    fun initBillingProcess(context: Context) {
        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                Log.i(TAG, "PurchasesUpdatedListener")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                    Log.i(TAG, "User cancelled purchase flow.")
                } else {
                    Log.i(TAG, "onPurchaseUpdated error: ${billingResult?.responseCode}")
                }
            }

        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        initBillingClient()
    }

    fun handlePurchase(purchase: Purchase) {
        // If your app has a server component, first verify the purchase by checking that the
        // purchaseToken hasn't already been used.

        // If purchase was a consumable product (a product you want the user to be able to buy again)
        handleConsumableProduct(purchase)

        // If purchase was non-consumable product
        handleNonConsumableProduct(purchase)
    }

    fun handleConsumableProduct(purchase: Purchase) {
        Log.d(TAG, "handleConsumableProduct: $purchase")

        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        billingClient.consumeAsync(consumeParams) { billingResult, _ ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                println("DENISA ------- handleConsumableProduct")
            }
        }
    }

    private fun handleNonConsumableProduct(purchase: Purchase) {
        Log.d(TAG, "handleNonConsumableProduct: $purchase")
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                billingClient.acknowledgePurchase(
                    acknowledgePurchaseParams.build()
                ) { }
            }
        }
    }

    fun launchBillingFlow(activity: Activity, skuDetails: SkuDetails) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        val responseCode = billingClient.launchBillingFlow(activity, flowParams)
        Log.i(TAG, "launchPurchaseFlow result $responseCode")

    }

    private fun initBillingClient() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                Log.d(TAG, "onBillingSetupFinished ${billingResult.responseCode}")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && billingIsReady()) {
                    queryPurchases(billingClient)
                    querySkuDetails(billingClient)
                } else {
                    Log.d(
                        TAG,
                        "Billing response code is not OK. Billing response code is: ${billingResult.responseCode}"
                    )
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "Billing service disconnected")
            }
        })
    }

    fun billingIsReady(): Boolean = billingClient.isReady

    fun getNextTruth(questionType: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = if (isTypePurchased(questionType)) {
                truthDaresRepository.getTruthWithIndex(partyModeTruthCounter++)
            } else {
                truthDaresRepository.getRandomLiteTruth(questionType)
            }
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getNextDare(questionType: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = if (isTypePurchased(questionType)) {
                truthDaresRepository.getDareWithIndex(partyModeDareCounter++)
            } else {
                truthDaresRepository.getRandomLiteDare(questionType)
            }
            liveData.postValue(randomTruth)
        }
        return liveData
    }

}
