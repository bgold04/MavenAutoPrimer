import os
import gc
from llama_cpp import Llama

# Load CodeLlama model
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"
llm = Llama(model_path=model_path)

# Java file path
java_file_path = "/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer/ApplicationMain.java"

# Read Java file
with open(java_file_path, "r") as file:
    java_code = file.readlines()

# 🔥 Reduce chunk size to fit context window (512 tokens)
chunk_size = 200  # Reducing from 700 to 200 to stay within limit
chunks = [java_code[i:i + chunk_size] for i in range(0, len(java_code), chunk_size)]

fixed_code = []

for i, chunk in enumerate(chunks):
    chunk_text = "".join(chunk)

    # 🔥 Shorter prompt to reduce token count
    prompt = f"Fix Java 8u40 errors in this code:\n{chunk_text}"

    try:
        # 🔥 Reduce `max_tokens` to 256 (was 1024)
        output = llm(prompt, max_tokens=256)
        corrected_chunk = output['choices'][0]['text']
        fixed_code.append(corrected_chunk)
        print(f"✅ Processed chunk {i+1}/{len(chunks)} ({len(chunk)} lines)")

        # Free memory
        gc.collect()

    except Exception as e:
        print(f"❌ Error processing chunk {i+1}: {e}")
        fixed_code.append(chunk_text)

# Save corrected Java file
corrected_file_path = java_file_path.replace(".java", "_fixed.java")
with open(corrected_file_path, "w") as fixed_file:
    fixed_file.write("\n".join(fixed_code))

print(f"\n✅ Fixed Java file saved to: {corrected_file_path}")
