from sympy import randprime ,mod_inverse
import random

def gcd(a, b):
    #helper function to calculate GCD
    while b:
        a, b = b, a % b
    return a

def generate_prime_candidate(max_val=10000):
    #helper function to generate random prime number in range [1000, max_val)
    return randprime(1000, max_val)

def generate_keys():
    #generate RSA keys
    p = generate_prime_candidate()
    q = generate_prime_candidate()

    while p == q: #ensure p and q are distinct
        q = generate_prime_candidate()

    n = p * q
    phi = (p-1) * (q-1) #Eulers totient function

    # determine public key exponent
    e = random.randrange(2, phi)
    while gcd(e, phi) != 1:
        e = random.randrange(2, phi)

    # determine private key exponent
    d = mod_inverse(e, phi)

    #return public and private keys
    return ((e, n), (d, n), p, q)

def encrypt(m, pubkey):
    #encrypt the message using the public key
    e, n = pubkey

    #convert each character to ciphertext
    cipher = [pow(ord(char), e, n) for char in m]
    return cipher

def decrypt(ciphertext, privkey):
    #decrypt the message using the private key
    d, n = privkey

    #generate the plaintext message
    m = [chr(pow(char, d, n)) for char in ciphertext]
    return ''.join(m)

#main function
def main():
    m = input("Enter the message: ")

    pubkey, privkey, p, q = generate_keys()
    print(f"Generated values:\np: {p}\nq: {q}\ne: {pubkey[0]}\nd: {privkey[0]}")

    encrypted_msg = encrypt(m, pubkey)
    print("Ciphertext:", encrypted_msg)

    decrypted_msg = decrypt(encrypted_msg, privkey)
    print("Decrypted message:", decrypted_msg)

if __name__ == "__main__":
    main()
