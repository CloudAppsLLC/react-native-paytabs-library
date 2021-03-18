package com.paytabs;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static com.payment.paymentsdk.integrationmodels.PaymentSdkTokenFormatKt.createPaymentSdkTokenFormat;
import static com.payment.paymentsdk.integrationmodels.PaymentSdkTokeniseKt.createPaymentSdkTokenise;

import com.google.gson.Gson;
import com.payment.paymentsdk.PaymentSdkActivity;
import com.payment.paymentsdk.PaymentSdkConfigBuilder;
import com.payment.paymentsdk.integrationmodels.PaymentSdkBillingDetails;
import com.payment.paymentsdk.integrationmodels.PaymentSdkConfigurationDetails;
import com.payment.paymentsdk.integrationmodels.PaymentSdkError;
import com.payment.paymentsdk.integrationmodels.PaymentSdkLanguageCode;
import com.payment.paymentsdk.integrationmodels.PaymentSdkShippingDetails;
import com.payment.paymentsdk.integrationmodels.PaymentSdkTokenFormat;
import com.payment.paymentsdk.integrationmodels.PaymentSdkTokenise;
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails;
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class RNPaytabsLibraryModule extends ReactContextBaseJavaModule implements CallbackPaymentInterface {

    private final ReactApplicationContext reactContext;
    private static int PAYMENT_REQUEST_CODE = 4040;
    private static String PAYTABS_MODULE = "RNPaytabsLibrary";
    private Callback mCallback;

    public RNPaytabsLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return PAYTABS_MODULE;
    }

    @ReactMethod
    public void log(String message) {
        Log.d(PAYTABS_MODULE, message);
    }

    @ReactMethod
    public void startCardPayment(String arguments, final Callback callback) {
        this.mCallback = callback;
        try {
            JSONObject paymentDetails = new JSONObject(arguments);
            String profileId = paymentDetails.getString("profileID");
            String serverKey = paymentDetails.getString("serverKey");
            String clientKey = paymentDetails.getString("clientKey");
            PaymentSdkLanguageCode locale = PaymentSdkLanguageCode.EN;
            String screenTitle = paymentDetails.getString("screenTitle");
            String orderId = paymentDetails.getString("cartID");
            String cartDesc = paymentDetails.getString("cartDescription");
            String currency = paymentDetails.getString("currency");
            String token = paymentDetails.getString("token");
            String transRef = paymentDetails.getString("transactionReference");
            double amount = paymentDetails.getDouble("amount");

            PaymentSdkTokenise tokeniseType = createPaymentSdkTokenise("tokeniseType");
            PaymentSdkTokenFormat tokenFormat = createPaymentSdkTokenFormat("tokenFormat");

            JSONObject billingDetails = paymentDetails.getJSONObject("billingDetails");

            PaymentSdkBillingDetails billingData = new PaymentSdkBillingDetails(
                    billingDetails.getString("city"),
                    billingDetails.getString("countryCode"),
                    billingDetails.getString("email"),
                    billingDetails.getString("name"),
                    billingDetails.getString("phone"), billingDetails.getString("state"),
                    billingDetails.getString("addressLine"), billingDetails.getString("zip")
            );

            JSONObject shippingDetails = paymentDetails.getJSONObject("shippingDetails");
            PaymentSdkShippingDetails shippingData = new PaymentSdkShippingDetails(
                    billingDetails.getString("city"),
                    billingDetails.getString("countryCode"),
                    billingDetails.getString("email"),
                    billingDetails.getString("name"),
                    billingDetails.getString("phone"), billingDetails.getString("state"),
                    billingDetails.getString("addressLine"), billingDetails.getString("zip")
            );
            PaymentSdkConfigurationDetails configData = new PaymentSdkConfigBuilder(
                    profileId, serverKey, clientKey, amount, currency)
                    .setCartDescription(cartDesc)
                    .setLanguageCode(locale)
                    .setBillingData(billingData)
                    .setMerchantCountryCode(paymentDetails.getString("merchantCountryCode"))
                    .setShippingData(shippingData)
                    .setCartId(orderId)
                    .setTokenise(tokeniseType, tokenFormat)
                    .setTokenisationData(token, transRef)
                    .showBillingInfo(paymentDetails.getBoolean("showBillingInfo"))
                    .showShippingInfo(paymentDetails.getBoolean("showShippingInfo"))
                    .forceShippingInfo(paymentDetails.getBoolean("forceShippingInfo"))
                    .setScreenTitle(screenTitle)
                    .build();
            String pt_samsung_token = paymentDetails.getString("pt_samsung_token");
            if (pt_samsung_token != null && pt_samsung_token.length() > 0)
                PaymentSdkActivity.startSamsungPayment(reactContext.getCurrentActivity(), configData, pt_samsung_token, this);
            else
                PaymentSdkActivity.startCardPayment(reactContext.getCurrentActivity(), configData, this);
        } catch (Exception e) {
            mCallback.invoke("0", e.getMessage(), "{}");
        }
    }

    @Override
    public void onError(@NotNull PaymentSdkError err) {
        mCallback.invoke(err.getCode() + "", err.getMsg(), new Gson().toJson(err));
    }

    @Override
    public void onPaymentFinish(@NotNull PaymentSdkTransactionDetails paymentSdkTransactionDetails) {
        if (mCallback != null)
            mCallback.invoke(new Gson().toJson(paymentSdkTransactionDetails));
    }

    @Override
    public void onPaymentCancel() {
        mCallback.invoke("0", "Cancelled", "{}");
    }
}