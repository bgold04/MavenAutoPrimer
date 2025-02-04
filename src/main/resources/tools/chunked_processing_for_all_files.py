import os
from llama_cpp import Llama

# Set model path
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"
llm = Llama(model_path=model_path)

# Java files directory
java_dir = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer"

# Function to process Java files in chunks
def process_java_file(file_path):
    with open(file_path, "r") as file:
        java_code = file.readlines()

    chunk_size = 300  # Keep under 512 tokens
    chunks = [java_code[i:i + chunk_size] for i in range(0, len(java_code), chunk_size)]

    fixed_code = []
    for i, chunk in enumerate(chunks):
        chunk_text = "".join(chunk)
        prompt = f"Fix errors in this Java 8u40 code and return only the corrected code:\n{chunk_text}"
        try:
            output = llm(prompt, max_tokens=512)
            corrected_chunk = output['choices'][0]['text']
            fixed_code.append(corrected_chunk)
            print(f"Processed chunk {i+1}/{len(chunks)} for {file_path}")
        except Exception as e:
            print(f"Error processing chunk {i+1} of {file_path}: {e}")
            fixed_code.append(chunk_text)

    fixed_file_path = file_path.replace(".java", "_fixed.java")
    with open(fixed_file_path, "w") as fixed_file:
        fixed_file.write("\n".join(fixed_code))

    return fixed_file_path

# Process all Java files in the directory
java_files = [os.path.join(java_dir, f) for f in os.listdir(java_dir) if f.endswith(".java")]
fixed_files = []

for java_file in java_files:
    fixed_path = process_java_file(java_file)
    fixed_files.append(fixed_path)

print("\n✅ Fixed Java files saved:")
for f in fixed_files:
    print(f" - {f}")
