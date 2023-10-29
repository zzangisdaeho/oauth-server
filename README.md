비밀키 생성코드 : keytool -genkeypair -alias privateKey -keyalg RSA -keypass issacIsHandsome -keystore privateKey.jks -storepass issacIsHandsome
공개키 생성코드 : keytool -list -rfc --keystore privateKey.jks | openssl x509 -inform pem -pubkey
