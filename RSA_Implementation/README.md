# RSA Encryption/Decryption

This project implements RSA encryption and decryption using Python's `sympy` library for generating prime numbers and calculating modular inverses.

## Description

The script generates RSA public and private keys, encrypts a message using the public key, and decrypts it using the private key. It uses the following steps:

1. Generate two distinct large prime numbers, `p` and `q`.
2. Compute `n = p * q` and `phi = (p-1) * (q-1)`.
3. Select an integer `e` such that `1 < e < phi` and `gcd(e, phi) = 1`.
4. Compute the private key `d` such that `e * d ≡ 1 (mod phi)`.
5. Encrypt the message by converting characters to their ASCII values and applying `cipher = char^e (mod n)`.
6. Decrypt the message by applying `char = cipher^d (mod n)` and converting back to characters.

## Usage

To run the script, execute the following command:

```bash
python rsa_encryption.py
