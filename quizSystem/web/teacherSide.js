/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


let quizContainer;
let questionCountInput;
let addQuizBtn;
let questionCount = 0;

document.addEventListener("DOMContentLoaded", function () {
    quizContainer = document.getElementById("quizContainer");
    addQuizBtn = document.getElementById("addQuizBtn");
    questionCountInput = document.getElementById("questionCount");

    function createQuizBlock(index, questionData = null) {
        const div = document.createElement('div');
        div.className = 'quiz-block border p-3 mb-3 rounded shadow-sm position-relative';
        div.id = `quizBlock${index}`;

        // Build 4 fixed options, use questionData if available
        let optionsHTML = '';
        for (let i = 0; i < 4; i++) {
            const optionId = questionData?.options?.[i]?.optionID ?? -1;
            const optionText = questionData?.options?.[i]?.optionText || '';
            const isCorrect = questionData?.options?.[i]?.isCorrect ? 'checked' : '';
            optionsHTML += `
                <div class="input-group mt-2">
                    <input type="hidden" name="optionId_${index}_${i + 1}" value="${optionId}">
                    <div class="input-group-text">
                        <input type="checkbox" name="isCorrect_${index}_${i + 1}" value="true" aria-label="Checkbox for option ${i + 1}" ${isCorrect}>
                    </div>
                    <input type="text" class="form-control" name="optionText_${index}_${i + 1}" placeholder="Option ${i + 1}" value="${optionText}" required>
                </div>
            `;
        }

        div.innerHTML = `
            <div class="d-flex justify-content-between align-items-start mb-2">
                <h5>Question ${index + 1}</h5>
                <div class="ms-auto d-flex align-items-center">
                    <label for="points${index}" class="form-label me-2 mb-0">Points:</label>
                    <input type="number" class="form-control d-inline-block" style="width: 80px;" id="points${index}" name="points${index}" min="1" value="${questionData?.points || 1}" required>
                </div>
            </div>

            <button type="button" class="btn btn-sm btn-danger mb-2" onclick="deleteQuestion(this, ${questionData?.questionID !== undefined ? `'${questionData.questionID}'` : 'null'})">Delete Question</button>

            <div class="mb-3">
                <input type="hidden" name="questionId${index}" value="${questionData?.questionID ?? -1}">
                <label for="questionText${index}" class="form-label">Question Text:</label>
                <input type="text" class="form-control" id="questionText${index}" name="questionText${index}" value="${questionData?.questionText || ''}" required>
            </div>

            <div class="multiple-section mb-3">
                <label>Options (check the correct ones):</label>
                ${optionsHTML}
            </div>
        `;

        quizContainer.appendChild(div);
    }

    function updateAllQuestionIndexes() {
        const blocks = quizContainer.querySelectorAll('.quiz-block');

        blocks.forEach((block, index) => {
            block.id = `quizBlock${index}`;
            block.querySelector("h5").innerText = "Question " + (index + 1);

            const pointsInput = block.querySelector('input[type="number"]');
            pointsInput.name = `points${index}`;
            pointsInput.id = `points${index}`;

            const pointsLabel = block.querySelector('label[for^="points"]');
            if (pointsLabel)
                pointsLabel.setAttribute('for', `points${index}`);

            const questionInput = block.querySelector('input[name^="questionText"]');
            questionInput.name = `questionText${index}`;
            questionInput.id = `questionText${index}`;

            const questionLabel = block.querySelector('label[for^="questionText"]');
            if (questionLabel)
                questionLabel.setAttribute('for', `questionText${index}`);

            // Update option names for fixed 4 options
            for (let i = 0; i < 4; i++) {
                const checkbox = block.querySelector(`input[type="checkbox"][name^="isCorrect_${index}_"]`);
                const optionCheckbox = block.querySelector(`input[name="isCorrect_${index}_${i + 1}"]`);
                if (optionCheckbox)
                    optionCheckbox.name = `isCorrect_${index}_${i + 1}`;
                const optionTextInput = block.querySelector(`input[name="optionText_${index}_${i + 1}"]`);
                if (optionTextInput)
                    optionTextInput.name = `optionText_${index}_${i + 1}`;
            }
        });

        questionCount = blocks.length;
        questionCountInput.value = questionCount;
    }

    window.deleteQuestion = function (button, questionId) {
        const block = button.closest('.quiz-block');

        if (window.isEditMode && questionId) {
            // Edit mode: delete from DB
            if (!confirm("Are you sure you want to delete this question?"))
                return;

            fetch('DeleteQuestionServlet?questionId=' + questionId)
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            if (block) {
                                block.remove();
                                updateAllQuestionIndexes();
                            }
                        } else {
                            alert("Failed to delete question.");
                        }
                    })
                    .catch(err => {
                        console.error("Error:", err);
                        alert("An error occurred.");
                    });
        } else {
            // Create mode: just remove from DOM
            if (block) {
                block.remove();
                updateAllQuestionIndexes();
            }
        }
    };

    addQuizBtn.addEventListener("click", () => {
        const currentCount = quizContainer.querySelectorAll('.quiz-block').length;
        createQuizBlock(currentCount);
        questionCount = currentCount + 1;
        questionCountInput.value = questionCount;

        const newBlock = document.getElementById(`quizBlock${currentCount}`);
        if (newBlock)
            newBlock.scrollIntoView({behavior: 'smooth'});
    });

    // Load existing questions if any
    if (typeof existingQuestions !== 'undefined' && Array.isArray(existingQuestions) && existingQuestions.length > 0) {
        existingQuestions.forEach((q, idx) => createQuizBlock(idx, q));
        questionCount = existingQuestions.length;
        questionCountInput.value = questionCount;
    }
});


const select = document.getElementById("is_published");

select.addEventListener('change', function () {
    if (this.value === "false") {
        this.classList.remove('text-success');
        this.classList.add('text-danger');

    } else if (this.value === "true") {
        this.classList.remove('text-danger');
        this.classList.add('text-success');
    } else {
        this.classList.remove('text-success', 'text-danger');
    }
});

//Triger on the page Load
select.dispatchEvent(new Event('change'));

