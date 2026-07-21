# PDF Processor
- Program built using Spring Boot and PDFBox library for processing and generating PDFs.

```
Core Layout
───────────
✅ Characters
✅ Words
✅ Lines
✅ Paragraphs

↓

Cleanup
────────
□ Header/Footer detection
□ Page number removal
□ Hyphenation repair
□ Paragraph merge across pages

↓

Structure
─────────
□ Headings
□ Lists
□ Tables
□ Images

↓

Advanced Layout
───────────────
□ Multi-column detection
□ Reading order
□ Footnotes
□ Captions

↓

Export
──────
□ Plain text
□ Markdown
□ HTML
□ JSON
```

---
## Running Ollama Locally
```shell 
ollama pull qwen2.5:1.5b
ollama pull llama3.2:1b 

ollama run qwen2.5:1.5b
```

> Prompt
``` 
You are a classifier.

Return ONLY valid JSON.

Categories:
- Billing
- Technical
- Shipping
- General

Text:
"My order hasn't arrived."
Classify the text.

Examples:

Text: "I can't login."
Category: Technical

Text: "Refund my money."
Category: Billing

Text: "Where is my package?"
Category: Shipping

Now classify:

Text:
"The tracking page hasn't updated."

Category:


Return:

{
  "category": ""
}
```

```
curl --request POST \
  --url http://localhost:11434/api/generate \
  --header 'Content-Type: application/json' \
  --data '{
  "model": "qwen2.5:1.5b",
  "prompt": "Classify the following text into Billing, Technical, Shipping, or General. Respond with only the category.\n\nText: My payment failed.",
  "stream": false
}'

{
  "category": "Shipping"
}
```

Best practices
Set temperature to 0 for deterministic outputs.
Limit num_predict to a small value (e.g., 10–20 tokens) since you only need a label.
Ask for one-word or JSON responses to simplify parsing.
Use few-shot examples if categories are subtle or domain-specific.

Example request:
```
{
    "model": "qwen2.5:1.5b",
    "prompt": "...",
    "temperature": 0,
    "num_predict": 10,
    "stream": false
}
```