from llama_cpp import Llama

# Set the correct model path
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"

# Load the model
llm = Llama(model_path=model_path)

# Read the contents of ApplicationMain.java
java_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain.java"
with open(java_file_path, "r") as file:
    java_code = file.readlines()

# Break the file into smaller chunks
chunk_size = 400  # Adjust chunk size to stay within token limits
chunks = [java_code[i:i + chunk_size] for i in range(0, len(java_code), chunk_size)]

fixed_code = []

for i, chunk in enumerate(chunks):
    chunk_text = "".join(chunk)
    prompt = f"Fix errors in this Java 8u40 code and return only the corrected code:\n{chunk_text}"

    try:
        output = llm(prompt, max_tokens=512)  # Ensure max_tokens fits within the model's limit
        corrected_chunk = output['choices'][0]['text']
        fixed_code.append(corrected_chunk)
        print(f"Processed chunk {i+1}/{len(chunks)}")
    except Exception as e:
        print(f"Error processing chunk {i+1}: {e}")
        fixed_code.append(chunk_text)  # Keep the original if an error occurs

# Save the corrected code to a new file
corrected_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain_fixed.java"
with open(corrected_file_path, "w") as fixed_file:
    fixed_file.write("\n".join(fixed_code))

print(f"Fixed Java file saved to: {corrected_file_path}")
