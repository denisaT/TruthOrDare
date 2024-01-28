package com.denisatrif.truthdare.billing

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.denisatrif.truthdare.R
import com.google.common.collect.ImmutableList

const val PARTY_PACK_ID = "party_pack"
const val SEXY_PACK_ID = "truth_dare_sexy_pack"
const val PURCHASES_STRING_SET = "purchases_string_set"

class BillingClientWrapper(
    private var context: Context,
    private var activity: Activity? = null,
) {
    private var settings: SharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Application.MODE_PRIVATE)

    private val billingClient =
        BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener { billingResult: BillingResult, purchases: MutableList<Purchase>? ->
                when (billingResult.responseCode) {

                    BillingResponseCode.USER_CANCELED,
                    BillingResponseCode.DEVELOPER_ERROR -> {
                        println("Developer error")
                        println(purchases)
                        println(billingResult.debugMessage)
                    }

                    BillingResponseCode.OK ->
                        if (purchases != null) {
                            for (purchase in purchases) {
                                //Save in settings
                                var purchaseStringSet = settings.getStringSet(PURCHASES_STRING_SET, emptySet())
                                purchaseStringSet = purchaseStringSet?.plus(purchase.products[0])
                                settings.edit().putStringSet(PURCHASES_STRING_SET, purchaseStringSet).apply()
                            }
                        }

                    BillingResponseCode.USER_CANCELED ->
                        println("DENISA -------- 2")

                    BillingResponseCode.ITEM_ALREADY_OWNED ->
                        if (purchases != null) {
                            for (purchase in purchases) {
                                handlePurchase(purchase)
//                                doWhenAlreadyOwned()
                            }
                        }


                }

            }.build()

    fun startConnection(launchFlow: Boolean = false, id: String? = null) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingResponseCode.OK) {
                    if (launchFlow) {
                        launchBillingForId(id.toString())
                    } else {
                        queryPurchasesAndSaveIfFound()
                    }
                }
            }

            override fun onBillingServiceDisconnected() {
                startConnection()
            }
        })
    }

    fun queryPurchasesAndSaveIfFound() {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
        billingClient.queryPurchasesAsync(params.build()) { billingResult, purchaseList ->
            if (billingResult.responseCode == BillingResponseCode.OK) {
                var stringSet = emptySet<String>()
                for (p in purchaseList) {
                    if (p.isAcknowledged) {
                        stringSet = stringSet.plus(p.products[0])
                    }
                }
                settings.edit().putStringSet(PURCHASES_STRING_SET, stringSet).apply()
            }
        }
    }

    fun launchBillingForId(id: String) {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder().setProductList(
            ImmutableList.of(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(id)
                    .setProductType(BillingClient.ProductType.INAPP).build(),
            )
        ).build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            when (billingResult.responseCode) {
                BillingResponseCode.OK -> launchBillingFlow(productDetailsList[0])
                BillingResponseCode.SERVICE_UNAVAILABLE,
                BillingResponseCode.SERVICE_DISCONNECTED -> startConnection(true, id)
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            //TODO
        }
    }

    private fun launchBillingFlow(productDetails: ProductDetails) {
        val billingFlowParams =
            BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(getDetailsParamsList(productDetails))
                .build()

        // Launch the billing flow
        if (activity != null) {
            val billingResult = billingClient.launchBillingFlow(activity!!, billingFlowParams)
        }
    }

    private fun getDetailsParamsList(
        productDetails: ProductDetails
    ): List<BillingFlowParams.ProductDetailsParams> {
        return listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )
    }

    fun shouldStartBillingFlow(id: String) = settings.getStringSet(PURCHASES_STRING_SET, emptySet())?.contains(id) != true

}


