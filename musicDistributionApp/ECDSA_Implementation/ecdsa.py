import random
import argparse

def setup_commands(parser):
    subparsers = parser.add_subparsers(dest="command", help="ECDSA commands")

    genkey_parser = subparsers.add_parser("genkey", help="Generate a key pair")
    genkey_parser.add_argument("p", type=int, help="Prime modulus")
    genkey_parser.add_argument("o", type=int, help="Curve order")
    genkey_parser.add_argument("xG", type=int, help="x-coordinate of base point G")
    genkey_parser.add_argument("yG", type=int, help="y-coordinate of base point G")

    sign_parser = subparsers.add_parser("sign", help="Sign a message")
    sign_parser.add_argument("p", type=int, help="Prime modulus")
    sign_parser.add_argument("o", type=int, help="Curve order")
    sign_parser.add_argument("xG", type=int, help="x-coordinate of base point G")
    sign_parser.add_argument("yG", type=int, help="y-coordinate of base point G")
    sign_parser.add_argument("private_key", type=int, help="Private key")
    sign_parser.add_argument("message_hash", type=int, help="Hash of the message to sign")

    verify_parser = subparsers.add_parser("verify", help="Verify a signature")
    verify_parser.add_argument("p", type=int, help="Prime modulus")
    verify_parser.add_argument("o", type=int, help="Curve order")
    verify_parser.add_argument("xG", type=int, help="x-coordinate of base point G")
    verify_parser.add_argument("yG", type=int, help="y-coordinate of base point G")
    verify_parser.add_argument("xQ", type=int, help="x-coordinate of public key Q")
    verify_parser.add_argument("yQ", type=int, help="y-coordinate of public key Q")
    verify_parser.add_argument("r", type=int, help="r component of signature")
    verify_parser.add_argument("s", type=int, help="s component of signature")
    verify_parser.add_argument("message_hash", type=int, help="Hash of the message")

    userid_parser = subparsers.add_parser("userid", help="Display user ID")

def handle_commands(args):
    if args.command == "genkey":
        genkey(args.p, args.o, args.xG, args.yG)
    elif args.command == "sign":
        sign(args.p, args.o, args.xG, args.yG, args.private_key, args.message_hash)
    elif args.command == "verify":
        verify(args.p, args.o, args.xG, args.yG, args.xQ, args.yQ, args.r, args.s, args.message_hash)
    elif args.command == "userid":
        userid()
    else:
        print("Unknown command")

def main():
    parser = argparse.ArgumentParser(description="ECDSA Implementation")
    setup_commands(parser)
    args = parser.parse_args()
    handle_commands(args)


def finite_field_addition(a, b, p):
    #perform addition on a finite field of size p
    return (a + b) % p

def finite_field_multiplication(a, b, p):
    #perform multiplication on a finite field of size p
    return (a * b) % p

def finite_field_exponentiation(base, exponent, p):
    #perform exponentiation on a finite field of size p
    return pow(base, exponent, p)

def finite_field_additive_inverse(x, p):
    #compute the additive inverse on a finite field of size p
    return (-x) % p

def finite_field_subtraction(a, b, p):
    b_inv = finite_field_additive_inverse(b, p)
    return (finite_field_addition(a, b_inv, p))

def finite_field_multiplicative_inverse(a, p):
    #compute the multiplicative inverse on a finite field of size p
    a = a % p #ensure a is in the range of the field
    return pow(a, p - 2, p)

def finite_field_division(a, b, p):
    b_inv = finite_field_multiplicative_inverse(b, p)
    return finite_field_multiplication(a, b_inv, p)

def elliptic_curve_addition(x1, y1, x2, y2, p):
    # check if either point is the point at infinity
    if x1 is None or y1 is None:
        return x2, y2
    if x2 is None or y2 is None:
        return x1, y1


    # Handle the case of adding a point to its reflection
    if x1 == x2 and y1 == (p - y2) % p:
        return None, None

    # ensure coordinates are within the field
    x1, y1 = x1 % p, y1 % p
    x2, y2 = x2 % p, y2 % p

    #point doubling when points are the same
    if x1 == x2 and y1 == y2:
        if y1 == 0:
            return None, None
        m = finite_field_division(finite_field_multiplication(3, finite_field_exponentiation(x1, 2, p), p), finite_field_multiplication(2, y1, p), p)
    else:
        #regular addition for distinct points
        m = finite_field_division(finite_field_subtraction(y2, y1, p), finite_field_subtraction(x2, x1, p), p)

    x3 = finite_field_subtraction(finite_field_subtraction(finite_field_exponentiation(m, 2, p), x1, p), x2, p)
    y3 = finite_field_subtraction(finite_field_multiplication(m, finite_field_subtraction(x1, x3, p), p), y1, p)
    return x3, y3

def elliptic_curve_multiplication(x, y, k, p):
    #start with the point at infinity
    xR, yR = None, None

    while k > 0:
        #if the current bit is 1, add R
        if k & 1:
            if xR is None:
                xR, yR = x, y
            else:
                xR, yR = elliptic_curve_addition(xR, yR, x, y, p)

            #double the point G
        x, y = elliptic_curve_addition(x, y, x, y, p)

        #move to the next bit
        k >>= 1

    return xR, yR


def generate_private_key(o):
    return random.randint(1, o - 1)

def generate_public_key(xG, yG, private_key, p):
    return elliptic_curve_multiplication(xG, yG, private_key, p)

def genkey (p, o, xG, yG):
    private_key = generate_private_key(o)
    public_key_x, public_key_y = generate_public_key(xG, yG, private_key, p)

    print(private_key)
    print(public_key_x)
    print(public_key_y)

def sign(p, o, xG, yG, private_key, message_hash):
    while True:
        k = random.randint(1, o - 1)
        k_inv = finite_field_multiplicative_inverse(k, o)
        xR, yR = elliptic_curve_multiplication(xG, yG, k, p)
        r = xR
        s = (k_inv * (message_hash + r * private_key)) % o

        if r != 0 and s != 0:
            print(r)
            print(s)
            break

def verify(p, o, xG, yG, xQ, yQ, r, s, message_hash):
    s_inv = finite_field_multiplicative_inverse(s, o)
    u1 = (s_inv * message_hash) % o
    u2 = (s_inv * r) % o
    x1, y1 = elliptic_curve_multiplication(xG, yG, u1, p)
    x2, y2 = elliptic_curve_multiplication(xQ, yQ, u2, p)
    xR_prime, yR_prime = elliptic_curve_addition(x1, y1, x2, y2, p)

    if xR_prime == r:
        print("True")
    else:
        print("False")
def userid():
    print("hnb2at")

if __name__ == "__main__":
    main()
