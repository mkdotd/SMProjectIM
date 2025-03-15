import requests
import os

token = os.environ.get("FRIENDLI_TOKEN") or ""

url = "https://api.friendli.ai/dedicated/v1/chat/completions"

headers = {
  "Authorization": "Bearer " + token,
  "Content-Type": "application/json"
}

payload = {
  "model": "f4dkp96oji0r",
  "messages": [
    {
      "role": "user",
      "content": "Can you create java unit test case for the following code ? package com.inventory;\n\nimport java.util.Scanner;\n\npublic class AddProduct {\n    private final InventoryManager inventoryManager;\n\n    public AddProduct(InventoryManager inventoryManager) {\n        this.inventoryManager = inventoryManager;\n    }\n\n    public void execute() {\n        Scanner scanner = new Scanner(System.in);\n        System.out.print(\"Enter Product ID: \");\n        int id = scanner.nextInt();\n        scanner.nextLine();\n        while(inventoryManager.findProductById(id) != null) {\n            System.out.print(\"Product already exists. Please choose another product ID: \");\n            id = scanner.nextInt();\n            scanner.nextLine();\n        }\n        System.out.print(\"Enter Product Name: \");\n        String name = scanner.nextLine();\n        while(inventoryManager.findProductByName(name) != null) {\n            System.out.print(\"Product Name already exists. Please choose another product Name: \");\n            name = scanner.nextLine();\n        }\n        System.out.print(\"Enter Quantity: \");\n        int quantity = scanner.nextInt();\n        System.out.print(\"Enter Price: \");\n        double price = scanner.nextDouble();\n        inventoryManager.addProduct(new Product(id, name, quantity, price));\n        System.out.println(\"Product added successfully.\");\n    }\n}\n"
    }
  ],
  "max_tokens": 2048,
  "top_p": 0.8,
  "stream": True,
  "stream_options": {
    "include_usage": True
  }
}

response = requests.request("POST", url, json=payload, headers=headers)

print(response.text)