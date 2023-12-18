package com.denisatrif.truthdare.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.google.firebase.crashlytics.internal.model.ImmutableList

const val PARTY_PACK_ID = "party_pack"
const val SEXY_PACK_ID = "truth_dare_sexy_pack"

class BillingClientWrapper constructor(
    private var context: Context,
    private var activity: Activity
) {

    private val billingClient =
        BillingClient.newBuilder(context).enablePendingPurchases()
            .setListener { billingResult: BillingResult, purchases: MutableList<Purchase>? ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        //handlePurchase(purchase)
                    }
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // Handle an error caused by a user cancelling the purchase flow.
                } else {
                    // Handle any other error codes.
                }
            }.build()

    fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                println("DISCONNECTED")
                startConnection()
            }
        })
    }

    fun queryProducts() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder().setProductList(
            ImmutableList.from(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(PARTY_PACK_ID)
                    .setProductType(BillingClient.ProductType.INAPP).build()
            )
        ).build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            println(billingResult)
            println(productDetailsList)

            launchBillingFlow(productDetailsList[0])
        }

    }

    fun launchBillingFlow(productDetails: ProductDetails) {
        val billingFlowParams =
            BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(getDetailsParamsList(productDetails))
                .build()

        // Launch the billing flow
        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
        println(billingResult)
    }

    private fun getDetailsParamsList(
        productDetails: ProductDetails
    ): List<BillingFlowParams.ProductDetailsParams> {
        return listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(productDetails.subscriptionOfferDetails?.get(0)?.offerToken!!)
                .build()
        )
    }
}


