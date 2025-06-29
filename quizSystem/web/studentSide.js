/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const quizContainer = document.getElementById("quizContainer");

    // Loop through the quiz questions
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

        // Check flag state from localStorage
        const isFlagged = localStorage.getItem(`flagged_${q.questionID}`) === 'true';

        // Append the flag emoji with the appropriate color (green for unflagged, red for flagged)
        div.innerHTML = `
            <div class="d-flex align-items-center">
                <!-- Flag Emoji (clickable and small) on the left of the question -->
                <span id="flag-${q.questionID}" class="flag ${isFlagged ? 'flagged' : 'unflagged'}" onclick="toggleFlag(${q.questionID}, this)">
                    \uD83C\uDFF3
                </span>
                <h5 class="ms-2">Question ${idx + 1}</h5>
            </div>
            <p>${q.questionText}</p>
            <div class="multiple-section">
                ${optionsHTML}
            </div>
        `;

        quizContainer.appendChild(div);
    });
});

// Function to toggle flag state
function toggleFlag(questionId, flagElement) {
    const isCurrentlyFlagged = flagElement.classList.contains('flagged');

    // Toggle the flag state
    if (isCurrentlyFlagged) {
        flagElement.classList.remove('flagged');
        flagElement.classList.add('unflagged');
        localStorage.setItem(`flagged_${questionId}`, 'false');
    } else {
        flagElement.classList.remove('unflagged');
        flagElement.classList.add('flagged');
        localStorage.setItem(`flagged_${questionId}`, 'true');
    }
}
