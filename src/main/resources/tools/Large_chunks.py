import os
import gc
from llama_cpp import Llama

# Load CodeLlama model (adjust path if needed)
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"
llm = Llama(model_path=model_path)

# Java file path (ApplicationMain.java)
java_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain.java"

# Read file
with open(java_file_path, "r") as file:
    java_code = file.readlines()

# **Large Chunking Approach** (600-800 lines per chunk)
chunk_size = 700  # High chunk size for better analysis
chunks = [java_code[i:i + chunk_size] for i in range(0, len(java_code), chunk_size)]

fixed_code = []

for i, chunk in enumerate(chunks):
    chunk_text = "".join(chunk)
    prompt = f"Fix errors in this Java 8u40 code and return only the corrected code:\n{chunk_text}"

    try:
        # **Higher token limit for deeper analysis**
        output = llm(prompt, max_tokens=1024)
        corrected_chunk = output['choices'][0]['text']
        fixed_code.append(corrected_chunk)
        print(f"✅ Processed chunk {i+1}/{len(chunks)} ({len(chunk)} lines)")

        # Free memory after each chunk
        gc.collect()

    except Exception as e:
        print(f"❌ Error processing chunk {i+1}: {e}")
        fixed_code.append(chunk_text)  # Preserve original if failure occurs

# Save the corrected Java file
corrected_file_path = java_file_path.replace(".java", "_fixed.java")
with open(corrected_file_path, "w") as fixed_file:
    fixed_file.write("\n".join(fixed_code))

print(f"\n✅ Fixed Java file saved to: {corrected_file_path}")
