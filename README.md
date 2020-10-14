
# react-native-paytabs-library
![Version](https://img.shields.io/badge/Paytabs%20React%20Native%20library-v1.1.0-beta-green)

React native paytabs library is a wrapper for the native PayTabs Android and iOS SDKs, It helps you integrate with PayTabs payment gateway.

Support:
* [x] iOS
* [x] Android

## Usage

# Installation

`$ npm install @paytabscom/react-native-paytabs-library@1.1.0-beta --save`

## Usage

1. Import `react-native-paytabs-library`

```javascript
import RNPaytabsLibrary from '@paytabscom/react-native-paytabs-library';
```
2. Configure arguments

```javascript
var args = {
  [RNPaytabsLibrary.merchant_email]: "test@example.com",
  [RNPaytabsLibrary.secret_key]: "merchant_secret_ket",
  [RNPaytabsLibrary.transaction_title]: "Mr. John Doe",
  [RNPaytabsLibrary.amount]: "2.0",
  [RNPaytabsLibrary.currency_code]: "USD",
  [RNPaytabsLibrary.customer_email]: "test@example.com",
  [RNPaytabsLibrary.customer_phone_number]: "+973000000",
  [RNPaytabsLibrary.order_id]: "1234567",
  [RNPaytabsLibrary.product_name]: "Tomato",
  [RNPaytabsLibrary.timeout_in_seconds]: "300", //Optional
  [RNPaytabsLibrary.address_billing]: "test test",
  [RNPaytabsLibrary.city_billing]: "Juffair",
  [RNPaytabsLibrary.state_billing]: "Manama",
  [RNPaytabsLibrary.country_billing]: "BHR",
  [RNPaytabsLibrary.postal_code_billing]: "00973", //Put Country Phone code if Postal code not available '00973'//
  [RNPaytabsLibrary.address_shipping]: "test test",
  [RNPaytabsLibrary.city_shipping]: "Juffair",
  [RNPaytabsLibrary.state_shipping]: "Manama",
  [RNPaytabsLibrary.country_shipping]: "BHR",
  [RNPaytabsLibrary.postal_code_shipping]: "00973", //Put Country Phone code if Postal
  [RNPaytabsLibrary.color]: "#cccccc",
  [RNPaytabsLibrary.language]: 'en', // 'en', 'ar'
  [RNPaytabsLibrary.tokenization]: true,
  [RNPaytabsLibrary.preauth]: false
}
```

3. Start calling payment method and handle the transaction details

```javascript
RNPaytabsLibrary.start(args, (response) => {
  // Response Code: 100 successful otherwise fail
  if (response.pt_response_code == '100')
    RNPaytabsLibrary.log("Transaction Id: " + response.pt_transaction_id);
  else
    RNPaytabsLibrary.log("Otherwise Response: " + response.pt_response_code);
});
```

## Demo application

Check our complete example here <https://github.com/paytabscom/react-native-paytabs-library/tree/master/example>.

<img src="images/demo.png" width="300">

## License

See [LICENSE][license].

## Paytabs

[Support][1] | [Terms of Use][2] | [Privacy Policy][3]

 [1]: https://www.paytabs.com/en/support/
 [2]: https://www.paytabs.com/en/terms-of-use/
 [3]: https://www.paytabs.com/en/privacy-policy/
 [license]: https://github.com/paytabscom/react-native-paytabs-library/blob/master/LICENSE