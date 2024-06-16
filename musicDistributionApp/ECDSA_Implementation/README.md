# ECDSA Implementation

This project implements the Elliptic Curve Digital Signature Algorithm (ECDSA) in Python. It includes functions for generating key pairs, signing messages, and verifying signatures.

## Description

The script supports the following commands:

- **genkey**: Generate a key pair.
  - Arguments: `p` (prime modulus), `o` (curve order), `xG` (x-coordinate of base point G), `yG` (y-coordinate of base point G)
  
- **sign**: Sign a message.
  - Arguments: `p` (prime modulus), `o` (curve order), `xG` (x-coordinate of base point G), `yG` (y-coordinate of base point G), `private_key` (private key), `message_hash` (hash of the message to sign)
  
- **verify**: Verify a signature.
  - Arguments: `p` (prime modulus), `o` (curve order), `xG` (x-coordinate of base point G), `yG` (y-coordinate of base point G), `xQ` (x-coordinate of public key Q), `yQ` (y-coordinate of public key Q), `r` (r component of signature), `s` (s component of signature), `message_hash` (hash of the message)
  
- **userid**: Display user ID.

## Notes

- This ECDSA implementation is for educational purposes only.
- Ensure you have Python installed to run the script.
