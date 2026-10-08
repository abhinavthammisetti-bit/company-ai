const API_URL = "http://localhost:8080/api/chat";

export async function askCompanyAIStream(companyName, question, onToken) {

    const response = await fetch(`${API_URL}/ask-stream`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "text/event-stream"
        },
        body: JSON.stringify({
            companyName,
            question
        })
    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    let buffer = "";

    while (true) {

        const { done, value } = await reader.read();

        if (done) break;

        buffer += decoder.decode(value, { stream: true });

        const events = buffer.split("\n\n");

        buffer = events.pop();

        for (const event of events) {

            const lines = event.split("\n");

            for (const line of lines) {

                if (!line.startsWith("data:")) continue;

                const data = line.substring(5).trim();

                if (data === "[DONE]") return;

                try {

                    const json = JSON.parse(data);

                    const token =
                        json.choices?.[0]?.delta?.content;

                    if (token !== undefined) {
                        onToken(token);
                    }

                } catch (e) {

                    console.log("Bad chunk", data);

                }

            }

        }

    }

}