import argparse
import rsa
import hashlib
from datetime import datetime
import binascii
import os
import re

def main():
    parser = argparse.ArgumentParser(description="Cryptocurrency Management System")
    setup_commands(parser)
    args = parser.parse_args()
    handle_commands(args)

def setup_commands(parser):
    #function to define and setup commands for the argument parser
    subparsers = parser.add_subparsers(dest="command", help="Commands") #create subparser for each command
    name_parser = subparsers.add_parser("name", help="Display the name of the cryptocurrency")
    generate_parser = subparsers.add_parser("generate", help="Generate a new wallet") #add a 'generate' command parser
    generate_parser.add_argument("wallet_name", help="Name of the wallet file")
    address_parser = subparsers.add_parser("address", help="Get the wallet tag from a wallet file")
    address_parser.add_argument("wallet_file", help="The wallet file to extract the tag from")
    fund_parser = subparsers.add_parser("fund", help="Fund a wallet")
    fund_parser.add_argument("wallet_tag", help="Tag of the wallet to fund")
    fund_parser.add_argument("amount", help="Amount to fund")
    fund_parser.add_argument("transaction_file", help="File to save the transaction statement")
    transfer_parser = subparsers.add_parser("transfer", help="Transfer funds between wallets")
    transfer_parser.add_argument("source_wallet_file", help="Source wallet file")
    transfer_parser.add_argument("destination_wallet_tag", help="Destination wallet tag")
    transfer_parser.add_argument("amount", help="Amount to transfer")
    transfer_parser.add_argument("transaction_file", help="File to save the transaction statement")
    verify_parser = subparsers.add_parser("verify", help="Verify a transaction")
    verify_parser.add_argument("wallet_file", help="Wallet file used in the transaction")
    verify_parser.add_argument("transaction_statement_file", help="File containing the transaction statement to be verified")
    balance_parser = subparsers.add_parser("balance", help="Balance of a wallet")
    balance_parser.add_argument("wallet_tag", help="Address of wallet")
    genesis_parser = subparsers.add_parser("genesis", help="Generate block 0 of blockchain")
    mine_parser = subparsers.add_parser("mine", help="add block to blockchain")
    mine_parser.add_argument("difficulty", help="number of leading zeros in hash value")
    validate_parser = subparsers.add_parser("validate", help="Verify hashes in blockchain")

    #additional commands can be added here

def handle_commands(args):
    #function to handle commands based on the arguments passed
    if args.command == "name":
        name()
    if args.command == "generate":
        generate(args.wallet_name)
    if args.command == "address":
        address(args.wallet_file)
    if args.command == "fund":
        fund(args.wallet_tag, args.amount, args.transaction_file)
    if args.command == "transfer":
        transfer(args.source_wallet_file, args.destination_wallet_tag, args.amount, args.transaction_file)
    if args.command == "verify":
        verify(args.wallet_file, args.transaction_statement_file)
    if args.command == "balance":
        balance(args.wallet_tag)
    if args.command == "genesis":
        genesis()
    if args.command == "mine":
        mine(args.difficulty)
    if args.command == "validate":
        validate()
    #logic for handling other commands can be added here

def name():
    print("MadAl")

def generate(wallet_name):
    public_key, private_key = rsa.newkeys(1024) #generate 1024 bit RSA key pair

    #convert keys to a string format
    public_key_pem = public_key.save_pkcs1().decode('utf-8')
    private_key_pem = private_key.save_pkcs1().decode('utf-8')

    #save the keys to the wallet file
    with open(wallet_name, 'w') as wallet_file:
        wallet_file.write(public_key_pem + "\n")
        wallet_file.write(private_key_pem + "\n")

    #generate a wallet tag (first 16 characters of the SHA-256 hash of the public key)
    public_key_encoded = ''.join(public_key_pem.split('\n')[1:-2]) #extract only the public key data
    wallet_tag = hashlib.sha256(public_key_encoded.encode('utf-8')).hexdigest()[:16]
    print(f"New wallet generated in '{wallet_name}' with tag {wallet_tag}")

def address(wallet_file_name):
    try:
        with open(wallet_file_name, 'r') as wallet_file:
            public_key = ''
            record = False
            for line in wallet_file:
                if '-----BEGIN RSA PUBLIC KEY-----' in line:
                    record = True
                    continue
                if '-----END RSA PUBLIC KEY-----' in line:
                    break
                if record:
                    public_key += line.strip()

            wallet_tag = hashlib.sha256(public_key.encode('utf-8')).hexdigest()[:16]
            print(wallet_tag)
    except Exception as e:
        print(f"Error reading wallet file: {e}")
        return None

def fund(wallet_tag, amount, transaction_file):
    try:
        #create transaction statement
        transaction_statement = (f"From: nero\n"
                                 f"To: {wallet_tag}\n"
                                 f"Amount: {amount}\n"
                                 f"Date: {current_timestamp()}\n")
        #in this case, no digital signature is needed

        #save to file
        with open(transaction_file, 'w') as file:
            file.write(transaction_statement)

        #convert transaction statement to transaction line and add to mempool
        transaction_line = transaction_statement_to_line(transaction_statement)
        add_to_mempool(transaction_line)

        print(f"Funded wallet {wallet_tag} with {amount} MadAl units on {current_timestamp()}")
    except Exception as e:
        print(f"Error in funding: {e}")

def transfer(source_wallet_file, destination_wallet_tag, amount, transaction_file):
    try:
        #load the wallet keys
        _, private_key = loadWallet(source_wallet_file)

        #create the transaction statement
        date = current_timestamp()
        transaction_statement = (f"From: {get_wallet_tag(source_wallet_file)}\n" \
                                f"To: {destination_wallet_tag}\n" \
                                f"Amount: {amount}\n" \
                                f"Date: {date}\n")

        #sign the transaction
        signature = rsa.sign(transaction_statement.strip().encode(), private_key, 'SHA-256')
        hex_signature = bytesToString(signature)
        #add the signature to the transaction statement
        transaction_statement += f"Signature: {hex_signature}\n"

        #save the transaction statement to file
        with open(transaction_file, 'w') as file:
            file.write(transaction_statement)

        print(f"Transferred {amount} from {source_wallet_file} to {destination_wallet_tag} and the statement to '{transaction_file}' on {date}")
    except Exception as e:
        print(f"Error in transfer: {e}")

def verify(wallet_file, transaction_statement_file):
    try:
        #load the public key from the wallet file
        public_key, _ = loadWallet(wallet_file)

        #read the transaction statement and extract the signature
        with open(transaction_statement_file, 'r') as file:
            content = file.read()
            lines = content.strip().split('\n')

            # Extract transaction details
            from_wallet = lines[0].split(': ')[1].strip()
            amount = int(lines[2].split(': ')[1].strip())

            #check if this is a funding transaction from nero
            if lines[0].startswith("From: nero"):
                print(f"Any funding request from nero is valid; written to mempool")
                return

            # Find the signature line
            signature_line = next((line for line in reversed(lines) if line.startswith("Signature: ")), None)
            if signature_line is None:
                raise ValueError("Signature format not recognized")

            # Extract the signature
            signature_hex = signature_line[len("Signature: "):].strip()[2:-1]
            signature = stringToBytes(signature_hex)

            # Exclude the signature line from the transaction statement
            transaction_statement = '\n'.join(line for line in lines if not line.startswith("Signature: "))

            #Calculate the balance of the sender
            sender_balance = get_balance(from_wallet)

            #Check if sender has sufficient funds
            if sender_balance < amount:
                raise ValueError("Insufficient funds for transaction")

            # Verify the signature
            rsa.verify(transaction_statement.strip().encode(), signature, public_key)

            #convert transaction statement to transaction line
            transaction_line = transaction_statement_to_line(transaction_statement)

            #add the verified transaction to the mempool
            add_to_mempool(transaction_line)

            print(f"The transaction in file '{transaction_statement_file}' with wallet '{wallet_file}' is valid, and was written to the mempool")
    except rsa.VerificationError:
        print(f"Verification failed: Invalid signature in transaction statement file '{transaction_statement_file}'")
    except Exception as e:
        print(f"Error in verifying transaction: {e}")

def balance(wallet_tag):
    total_balance = 0

    #process transactions in the blockchain
    total_balance += process_blockchain_transactions(wallet_tag)

    #process transactions in the mempool
    total_balance += process_mempool_transactions(wallet_tag)

    #print the total balance
    print(total_balance)

def genesis():
    genesis_quote = "Worrying is using your imagination to create something you don't want"

    # Create an arbitrary hash for the genesis block.
    arbitrary_hash = hashlib.sha256("Genesis Block".encode('utf-8')).hexdigest()

    # Create the genesis block content with the arbitrary hash
    genesis_block_content = f"{arbitrary_hash}\n\n{genesis_quote}\n\nnonce: 0"

    # Write the genesis block content to block_0.txt
    with open('block_0.txt', 'w') as file:
        file.write(genesis_block_content)

    print("Genesis block created in 'block_0.txt'")

def mine(difficulty):
    #convert difficulty to an int
    difficulty = int(difficulty)

    #determine the filename of the last block
    last_block_filename = get_last_block_filename()

    if last_block_filename is None:
        print("No previous block found. Cannot mine a new block.")
        return

    #use hashFile to get the hash of the last block
    prev_block_hash = hashFile(last_block_filename)

    #load transactions from the mempool
    with open('mempool.txt', 'r') as mempool_file:
        transactions = mempool_file.readlines()

    #Ensure transactions are newline separated
    transactions_str = '\n'.join([t.strip() for t in transactions]) + '\n' if transactions else ''

    #mining process
    nonce = 0
    while True:
        block_content = f"{prev_block_hash}\n{transactions_str}nonce: {nonce}\n"
        block_hash = hashlib.sha256(block_content.encode('utf-8')).hexdigest()

        # Check if hash meets the difficulty criteria
        if block_hash.startswith('0' * difficulty):
            break
        nonce += 1

    #create new block
    block_number = get_next_block_number()
    block_filename = f'block_{block_number}.txt'
    with open(block_filename, 'w') as block_file:
        block_file.write(block_content)

    #update mempool by removing included transactions
    clear_mempool(transactions)

    print(f"Mempool transactions moved to {block_filename} and mined with difficulty {difficulty} and nonce {nonce}")

def validate():
    blockchain_files = sorted(get_blockchain_files(), key=lambda f: int(f.split('_')[1].split('.')[0]))

    #skip verification if only genesis block exists or no blocks exist
    if (len(blockchain_files) <= 1):
        print(True)

    #start validation from the second block (block_1.txt)
    for i in range(1, len(blockchain_files)):
        current_block_file = blockchain_files[i]
        previous_block_file = blockchain_files[i-1]

        #read the stored hash from the current block
        with open(current_block_file, 'r') as file:
            stored_previous_hash = file.readline().strip()

        #calculate the actual hash of the previous block
        actual_previous_hash = hashFile(previous_block_file)

        #compare the stored hash with the actual hash
        if stored_previous_hash != actual_previous_hash:
            print(False)

    print(True) #blockchain is valid

def get_last_block_filename():
    #block files stored in current directory
    blockchain_dir = '.'

    #list all files in directory
    files = os.listdir(blockchain_dir)

    #filter out blockchain files
    block_files = [file for file in files if file.startswith('block_') and file.endswith('.txt')]

    #sort the files by block number
    block_files.sort(key=lambda f: int(f.split('_')[1].split('.')[0]))

    #return the last block files name
    if block_files:
        return block_files[-1]
    else:
        return None #return none if no block files are found

def get_next_block_number():
    #block files stored in current directory
    blockchain_dir = '.'

    #list all files in the directory
    files = os.listdir(blockchain_dir)

    #filter out blockchain files and extract numbers
    block_numbers = [int(re.search(r'block_(\d+).txt', file).group(1)) for file in files if
                     re.match(r'block_\d+.txt', file)]

    #determine the next block number
    if block_numbers:
        return max(block_numbers) + 1
    else:
        return 0 #return 0 if no block files found, indicating that the next block is the first block

def clear_mempool(included_transactions):
    mempool_filename = 'mempool.txt'

    #read all transactions from the mempool
    with open(mempool_filename, 'r') as file:
        all_transactions = file.readlines()

    #filter out the transactions that were included in the mined block
    remaining_transactions = [t for t in all_transactions if t not in included_transactions]

    #write the remaining transactions back to the mempool
    with open(mempool_filename, 'w') as file:
        file.writelines(remaining_transactions)

def hashFile(filename):
    h = hashlib.sha256()
    with open(filename, 'rb', buffering=0) as f:
        for b in iter(lambda : f.read(128*1024), b''):
            h.update(b)
    return h.hexdigest()

def get_balance(wallet_tag):
    total_balance = 0

    # process transactions in the blockchain
    total_balance += process_blockchain_transactions(wallet_tag)

    # process transactions in the mempool
    total_balance += process_mempool_transactions(wallet_tag)

    # print the total balance
    return total_balance

def process_blockchain_transactions(wallet_tag):
    balance = 0

    blockchain_transactions = get_blockchain_transactions()
    if blockchain_transactions is None:
        return balance #if there are no blockchain transactions, return the current balance

    for transaction in blockchain_transactions:
        from_wallet, to_wallet, amount = parse_transaction_line(transaction)
        if from_wallet == wallet_tag:
            balance -= int(amount)
        elif to_wallet == wallet_tag:
            balance += int(amount)

    return balance

def process_mempool_transactions(wallet_tag):
    balance = 0
    #open the mempool file and process each transaction
    with open('mempool.txt', 'r') as mempool_file:
        for line in mempool_file:
            from_wallet, to_wallet, amount = parse_transaction_line(line)
            if from_wallet == wallet_tag:
                balance -= int(amount)
            elif to_wallet == wallet_tag:
                balance += int(amount)

    return balance

def get_blockchain_transactions():
    all_transactions = []

    #get the list of all blockchain files sorted by block number
    blockchain_files = sorted(get_blockchain_files(), key=lambda f: int(f.split('_')[1].split('.')[0]))

    #iterate over each blockchain transaction
    for block_file in blockchain_files:
        block_number = int(block_file.split('_')[1].split('.')[0])
        if block_number == 0:  # Skip genesis block
            continue
        #read the content of the block file
        with open(block_file, 'r') as file:
            lines = file.readlines()

        transactions = lines[1:-1]

        #add the all_transactions list
        all_transactions.extend(transactions)

    return all_transactions

def get_blockchain_files():
    return [file for file in os.listdir('.') if re.match(r'block_\d+\.txt', file)]

def parse_transaction_line(transaction_line):
    #parse a transaction line and return the from_wallet, to_wallet, and amount
    try:
        # Remove any leading/trailing whitespace and skip empty lines
        transaction_line = transaction_line.strip()
        if not transaction_line:
            return None, None, None

        parts = transaction_line.split()
        # Check if the line has the expected number of parts
        if len(parts) < 5:
            raise ValueError("Transaction line does not have enough parts")

        from_wallet = parts[0]
        to_wallet = parts[4]
        amount = parts[2]

        # Additional checks for safety
        if not amount.isdigit():
            raise ValueError("Amount is not a valid number")

        return from_wallet, to_wallet, amount

    except Exception as e:
        print(f"Error parsing the transaction line '{transaction_line}': {e}")
        return None, None, None

def get_wallet_tag(wallet_file):
    try:
        with open(wallet_file, 'r') as wallet_file:
            public_key = ''
            record = False
            for line in wallet_file:
                if '-----BEGIN RSA PUBLIC KEY-----' in line:
                    record = True
                    continue
                if '-----END RSA PUBLIC KEY-----' in line:
                    break
                if record:
                    public_key += line.strip()

            wallet_tag = hashlib.sha256(public_key.encode('utf-8')).hexdigest()[:16]
            return(wallet_tag)
    except Exception as e:
        print(f"Error reading wallet file: {e}")
        return None

def loadWallet(filename):
    with open(filename, mode='rb') as file:
        keydata = file.read()
    privkey = rsa.PrivateKey.load_pkcs1(keydata)
    pubkey = rsa.PublicKey.load_pkcs1(keydata)
    return pubkey, privkey

def stringToBytes(hexstr):
    return binascii.a2b_hex(hexstr)

def bytesToString(data):
    return binascii.hexlify(data)

def current_timestamp():
    now = datetime.now()
    timestamp = now.strftime("%a %b %d %H:%M:%S %Z %Y")
    return timestamp

def transaction_statement_to_line(statement):
    #break the statement into lines
    lines = statement.split('\n')

    #extract relevant information
    from_wallet = lines[0].split(': ')[1].strip() if len(lines) > 0 else ''
    to_wallet = lines[1].split(': ')[1].strip() if len(lines) > 1 else ''
    amount = lines[2].split(': ')[1].strip() if len(lines) > 2 else ''
    date = lines[3].split(': ')[1].strip() if len(lines) > 3 else ''

    #construct the transaction line
    transaction_line = f"{from_wallet} transferred {amount} to {to_wallet} on {date}"
    return transaction_line

def add_to_mempool(transaction_line):
    try:
        #open the mempool.txt file in append mode
        with open('mempool.txt', 'a') as mempool_file:
            #write the transaction line to the mempool
            mempool_file.write(transaction_line + '\n')

    except IOError as e:
        print("Error adding transaction to mempool: {e}")

if __name__ == "__main__":
    main()  # Execute the main function if the script is run as the main program
