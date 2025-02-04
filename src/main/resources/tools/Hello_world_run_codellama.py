from llama_cpp import Llama

# Set the correct model path
model_path = "/home/bgold/CodeLlama-13B-GGUF/codellama-13b.Q4_K_M.gguf"

# Load the model
llm = Llama(model_path=model_path)

# Define the Java 8 fix prompt
prompt = "Fix all errors in this Java 8u40 code:\npublic class Test { public static void main(String[] args) { System.out.println(\"Hello, world!\"); } }"

# Generate output
output = llm(prompt, max_tokens=200)

# Extract the fixed code
fixed_code = output['choices'][0]['text']

# Print it properly
print("Fixed Java Code:\n")
print(fixed_code)