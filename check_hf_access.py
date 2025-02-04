from huggingface_hub import list_repo_files, HfApi

repo_id = "meta-llama/CodeLlama-13b-hf"
api = HfApi()

try:
    files = list_repo_files(repo_id)
    print("You have access to the repository. Listing files:")
    for file in files:
        print(file)
except Exception as e:
    print(f"Error: {e}")