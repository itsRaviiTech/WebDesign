document.addEventListener("DOMContentLoaded", function () {
    const container = document.getElementById("quizContainer");

    if (!quizData || quizData.length === 0) {
        container.innerHTML = "<p class='text-danger'>No questions available for review.</p>";
        return;
    }

    quizData.forEach((question, index) => {
        const questionBlock = document.createElement("div");
        questionBlock.className = "quiz-block p-3 border rounded shadow-sm";

        const questionHeader = document.createElement("h5");
        questionHeader.textContent = `Q${index + 1}: ${question.questionText}`;
        questionBlock.appendChild(questionHeader);

        const optionList = document.createElement("div");

        question.options.forEach((option, optIndex) => {
            const optionDiv = document.createElement("div");
            optionDiv.className = "p-2 rounded mb-2";

            // logic for highlight class
            const isCorrect = option.isCorrect;
            const isSelected = option.isSelected;

            if (isCorrect && isSelected) {
                optionDiv.classList.add("highlight-correct");
            } else if (!isCorrect && isSelected) {
                optionDiv.classList.add("highlight-wrong");
            } else if (isCorrect && !isSelected) {
                optionDiv.classList.add("highlight-missed");
            }

            optionDiv.innerHTML = `
                <strong>${String.fromCharCode(65 + optIndex)}.</strong> ${option.optionText}
                ${isCorrect ? "<span class='badge bg-success ms-2'>Correct</span>" : ""}
                ${isSelected ? "<span class='badge bg-primary ms-2'>Your Answer</span>" : ""}
            `;

            optionList.appendChild(optionDiv);
        });

        questionBlock.appendChild(optionList);
        container.appendChild(questionBlock);
    });
});
