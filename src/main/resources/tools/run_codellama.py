from llama_cpp import Llama

# Set the correct model path
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"

# Load the model
llm = Llama(model_path=model_path)

# Read the contents of ApplicationMain.java
java_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain.java"
with open(java_file_path, "r") as file:
    java_code = file.read()

# Create a prompt for fixing the Java code
prompt = f"Fix all errors in this Java 8u40 code and return only the corrected code:\n{java_code}"

# Generate a response
output = llm(prompt, max_tokens=4096)  # Adjust max_tokens if needed

# Extract the fixed Java code
fixed_code = output['choices'][0]['text']

# Save the corrected code to a new file
corrected_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain_fixed.java"
with open(corrected_file_path, "w") as fixed_file:
    fixed_file.write(fixed_code)

print(f"Fixed Java file saved to: {corrected_file_path}")
