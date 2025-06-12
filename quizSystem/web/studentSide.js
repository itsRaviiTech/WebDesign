/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener("DOMContentLoaded", function () {
    const quizContainer = document.getElementById("quizContainer");

    quizData.forEach((q, idx) => {
        const div = document.createElement("div");
        div.className = "quiz-block border p-3 mb-3 rounded shadow-sm";
        div.id = `quizBlock${idx}`;

        // Ensure unique name per question
        const questionName = `question_${q.questionID || idx}`;

        let optionsHTML = '';
        q.options.forEach((opt) => {
            optionsHTML += `
                <div class="form-check mt-2">
                    <input class="form-check-input" type="radio" 
                           name="${questionName}" 
                           value="${opt.optionID}" 
                           id="q${q.questionID}_opt${opt.optionID}">
                    <label class="form-check-label" for="q${q.questionID}_opt${opt.optionID}">
                        ${opt.optionText}
                    </label>
                </div>
            `;
        });

        div.innerHTML = `
            <h5>Question ${idx + 1}</h5>
            <p>${q.questionText}</p>
            <div class="multiple-section">
                ${optionsHTML}
            </div>
        `;

        quizContainer.appendChild(div);
    });
});


