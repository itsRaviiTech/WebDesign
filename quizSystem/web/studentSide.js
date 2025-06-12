/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", function () {
    const quizContainer = document.getElementById("quizContainer");
    const submitBtn = document.getElementById("submitQuiz");

    if (typeof quizData === "undefined") {
        console.error("quizData is not defined");
        return;
    }

    function createAttemptBlock(index, questionData) {
    const div = document.createElement("div");
    div.className = "quiz-block border p-3 mb-3 rounded shadow-sm";
    div.id = `quizBlock${index}`;

    let optionsHTML = '';
    questionData.options.forEach((opt) => {
        optionsHTML += `
            <div class="form-check mt-2">
                <input class="form-check-input" type="checkbox" 
                       name="question_${questionData.questionId}" 
                       value="${opt.optionId}" 
                       id="q${questionData.questionId}_opt${opt.optionId}">
                <label class="form-check-label" for="q${questionData.questionId}_opt${opt.optionId}">
                    ${opt.optionText}
                </label>
            </div>
        `;
    });

    div.innerHTML = `
        <h5>Question ${index + 1}</h5>
        <p>${questionData.questionText}</p>
        <div class="multiple-section">
            ${optionsHTML}
        </div>
    `;

    quizContainer.appendChild(div);
}


    quizData.forEach((q, idx) => createAttemptBlock(idx, q));
submitBtn.addEventListener("click", function () {
    const answers = quizData.map((question) => {
        const selectedOptions = Array.from(document.querySelectorAll(`input[name="question_${question.questionId}"]:checked`))
            .map(opt => opt.value);

        return {
            questionId: question.questionId,
            selectedOptionIds: selectedOptions,  // array of selected options
        };
    });

    submitBtn.disabled = true;

    fetch("SubmitQuizServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ answers }),
    })
    .then((res) => {
        if (!res.ok) throw new Error("Failed to submit quiz");
        return res.text();
    })
    .then((msg) => {
        alert("Quiz submitted!\n" + msg);
    })
    .catch((err) => {
        console.error(err);
        alert("Error submitting quiz.");
        submitBtn.disabled = false;
    });
});

});


