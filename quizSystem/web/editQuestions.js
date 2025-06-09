/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    let questionCount = parseInt(document.getElementById("questionCount").value);
    const quizContainer = document.getElementById("quizContainer");
    const addBtn = document.getElementById("addQuestionBtn");

    function createQuestionBlock(index) {
        const div = document.createElement('div');
        div.className = 'quiz-block border p-3 mb-3 rounded shadow-sm';
        div.id = `quizBlock${index}`;
        div.innerHTML = `
            <h5>New Question</h5>
            <input type="hidden" name="questionId${index}" value="new" />
            <label>Question Text:</label>
            <input type="text" name="questionText${index}" class="form-control mb-2" required>

            <label>Points:</label>
            <input type="number" name="points${index}" class="form-control mb-2" min="1" value="1" required>

            <label>Options:</label>
            ${generateOptions(index)}

            <button type="button" class="btn btn-sm btn-danger mt-2" onclick="deleteQuestionBlock(${index})">Delete</button>
        `;
        quizContainer.appendChild(div);
    }

    function generateOptions(qIndex) {
        let html = "";
        for (let i = 0; i < 4; i++) {
            html += `
            <div class="input-group mb-2">
                <div class="input-group-text">
                    <input type="checkbox" name="isCorrect_${qIndex}_${i}" />
                </div>
                <input type="text" class="form-control" name="optionText_${qIndex}_${i}" placeholder="Option ${i + 1}" required>
            </div>`;
        }
        return html;
    }

    addBtn.addEventListener("click", () => {
        createQuestionBlock(questionCount);
        questionCount++;
        document.getElementById("questionCount").value = questionCount;
    });
});

function deleteQuestionBlock(index) {
    const block = document.getElementById(`quizBlock${index}`);
    if (block) block.remove();
}


