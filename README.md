
# react-native-clickpay
![Version](https://img.shields.io/badge/React%20Native%20ClickPay-v2.0.3-green)

React native ClickPay library is a wrapper for the native ClickPay Android and iOS SDKs, It helps you integrate with ClickPay payment gateway.

Library Support:

* [x] iOS
* [x] Android

# Installation

`$ npm install @paytabs/react-native-clickpay@2.0.4 --save`

## Usage

Import `react-native-clickpay`

```javascript
import {RNPaymentSDKLibrary, PaymentSDKConfiguration, PaymentSDKBillingDetails, PaymentSDKTheme} from '@paytabs/react-native-clickpay';
```

### Pay with Card

1. Configure the billing & shipping info, the shipping info is optional

```javascript
let billingDetails = new PaymentSDKBillingDetails(name= "John Smith",
                                  email= "email@test.com",
                                  phone= "+ 2011111111",
                                  addressLine= "address line",
                                  city= "Dubai",
                                  state= "Dubai",
                                  countryCode= "ae", // ISO alpha 2
                                  zip= "1234")

let shippingDetails = new PaymentSDKShippingDetails(name= "John Smith",
                                  email= "email@test.com",
                                  phone= "+ 2011111111",
                                  addressLine= "address line",
                                  city= "Dubai",
                                  state= "Dubai",
                                  countryCode= "ae", // ISO alpha 2
                                  zip= "1234")
                                              
```

2. Create object of `PaymentSDKConfiguration` and fill it with your credentials and payment details.

```javascript

let configuration = new PaymentSDKConfiguration();
    configuration.profileID = "*your profile id*"
    configuration.serverKey= "*server key*"
    configuration.clientKey = "*client key*"
    configuration.cartID = "545454"
    configuration.currency = "AED"
    configuration.cartDescription = "Flowers"
    configuration.merchantCountryCode = "ae"
    configuration.merchantName = "Flowers Store"
    configuration.amount = 20
    configuration.screenTitle = "Pay with Card"
    configuration.billingDetails = billingDetails
	 configuration.forceShippingInfo = false
```

Options to show billing and shipping ifno

```javascript

	configuration.showBillingInfo = true
	configuration.showShippingInfo = true
	
```

3. Start payment by calling `startCardPayment` method and handle the transaction details 

```javascript

RNPaymentSDKLibrary.startCardPayment(JSON.stringify(configuration)).then( result => {
      if(result["PaymentDetails"] != null) { // Handle transaction details
        let paymentDetails = result["PaymentDetails"]
        console.log(paymentDetails)
      } else if(result["Event"] == "CancelPayment") { // Handle events
        console.log("Cancel Payment Event")
      } 
     }, function(error) { // Handle error
      console.log(error)
     });
     
```

### Pay with Apple Pay

1. Follow the guide [Steps to configure Apple Pay][applepayguide] to learn how to configure ApplePay with ClickPay.

2. Do the steps 1 and 2 from **Pay with Card** although you can ignore Billing & Shipping details and Apple Pay will handle it, also you must pass the **merchant name** and **merchant identifier**.

```javascript

let configuration = new PaymentSDKConfiguration();
    configuration.profileID = "*your profile id*"
    configuration.serverKey= "*server key*"
    configuration.clientKey = "*client key*"
    configuration.cartID = "545454"
    configuration.currency = "AED"
    configuration.cartDescription = "Flowers"
    configuration.merchantCountryCode = "ae"
    configuration.merchantName = "Flowers Store"
    configuration.amount = 20
    configuration.screenTitle = "Pay with Card"
    configuration.merchantIdentifier = "merchant.com.bundleID"

```

3. To simplify ApplePay validation on all user's billing info, pass **simplifyApplePayValidation** parameter in the configuration with **true**.

```javascript

configuration.simplifyApplePayValidation = true

```

4. Call `startApplePayPayment` to start payment

```javascript
RNPaymentSDKLibrary.startApplePayPayment(JSON.stringify(configuration)).then( result => {
        if(result["PaymentDetails"] != null) { // Handle transaction details
          let paymentDetails = result["PaymentDetails"]
          console.log(paymentDetails)
        } else if(result["Event"] == "CancelPayment") { // Handle events
          console.log("Cancel Payment Event")
        } 
     }, function(error) { // handle errors
      console.log(error)
     });
```

### Pay with Samsung Pay

Pass Samsung Pay token to the configuration and call `startCardPayment`

```javascript
configuration.samsungToken = "token"
```

## Enums

Those enums will help you in customizing your configuration.

* Tokenise types

 The default type is none

```javascript
const TokeniseType = Object.freeze({
"none":"none", // tokenise is off
"merchantMandatory":"merchantMandatory", // tokenise is forced
"userMandatory":"userMandatory", // tokenise is forced as per user approval
"userOptinoal":"userOptional" // tokenise if optional as per user approval
});
```

```javascript
configuration.tokeniseType = TokeniseType. userOptinoal
```

* Token formats

The default format is hex32

```javascript
const TokeniseFromat = Object.freeze({"none":"1", 
"hex32": "2", 
"alphaNum20": "3", 
"digit22": "3", 
"digit16": "5", 
"alphaNum32": "6"
});
```

```javascript
configuration.tokeniseFormat = TokeniseFromat.hex32
```
## Demo application

Check our complete example [here][example].

<img src="https://user-images.githubusercontent.com/13621658/109432386-905e5280-7a13-11eb-847c-63f2c554e2d1.png" width="370">

## License

See [LICENSE][license].

## ClickPay

[Support][1] | [Terms of Use][2] | [Privacy Policy][3]

 [1]: https://merchant.clickpay.com.sa/
 [2]: https://merchant.clickpay.com.sa
 [3]: https://merchant.clickpay.com.sa
 [license]: https://github.com/paytabscom/react-native-paytabs-library/blob/clickpay/LICENSE
 [applepayguide]: https://github.com/paytabscom/react-native-paytabs-library/blob/clickpay/ApplePayConfiguration.md
 [example]: https://github.com/paytabscom/react-native-paytabs-library/tree/clickpay/example

