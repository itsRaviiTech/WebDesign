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

        const questionType = questionData?.type || 'Multiple Choice';
        const isTF = questionType === 'True/False';

        let optionsHTML = '';
        if (isTF) {
            const trueChecked = questionData?.options?.[0]?.isCorrect ? 'checked' : '';
            const falseChecked = questionData?.options?.[1]?.isCorrect ? 'checked' : '';
            optionsHTML = `
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="isCorrect_${index}" id="trueOption_${index}" value="true" ${trueChecked} required>
                    <label class="form-check-label" for="trueOption_${index}">True</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="isCorrect_${index}" id="falseOption_${index}" value="false" ${falseChecked}>
                    <label class="form-check-label" for="falseOption_${index}">False</label>
                </div>
            `;
        } else {
            for (let i = 0; i < 4; i++) {
                const optionId = questionData?.options?.[i]?.optionID ?? -1;
                const optionText = questionData?.options?.[i]?.optionText || '';
                const isCorrect = questionData?.options?.[i]?.isCorrect ? 'checked' : '';
                optionsHTML += `
                    <div class="input-group mt-2">
                        <input type="hidden" name="optionId_${index}_${i + 1}" value="${optionId}">
                        <div class="input-group-text">
                            <input type="checkbox" name="isCorrect_${index}_${i + 1}" value="true" ${isCorrect}>
                        </div>
                        <input type="text" class="form-control" name="optionText_${index}_${i + 1}" placeholder="Option ${i + 1}" value="${optionText}" required>
                    </div>
                `;
            }
        }

        div.innerHTML = `
            <div class="d-flex justify-content-between align-items-start mb-2">
                <h5>Question ${index + 1}</h5>
                <div class="ms-auto d-flex align-items-center">
                    <label for="points${index}" class="form-label me-2 mb-0">Points:</label>
                    <input type="number" class="form-control d-inline-block" style="width: 80px;" id="points${index}" name="points${index}" min="1" value="${questionData?.points || 1}" required>
                </div>
            </div>

            <button type="button" class="btn btn-sm btn-danger mb-2" onclick="deleteQuestion(this, ${questionData?.questionID ?? 'null'})">Delete Question</button>

            <div class="mb-2">
                <label for="questionType${index}" class="form-label">Question Type:</label>
                <select class="form-select question-type-select" name="questionType${index}" id="questionType${index}" data-index="${index}">
                    <option value="Multiple Choice" ${questionType === 'Multiple Choice' ? 'selected' : ''}>Multiple Choice</option>
                    <option value="True/False" ${questionType === 'True/False' ? 'selected' : ''}>True/False</option>
                </select>
            </div>

            <div class="mb-3">
                <input type="hidden" name="questionId${index}" value="${questionData?.questionID ?? -1}">
                <label for="questionText${index}" class="form-label">Question Text:</label>
                <input type="text" class="form-control" id="questionText${index}" name="questionText${index}" value="${questionData?.questionText || ''}" required>
            </div>

            <div class="multiple-section mb-3">
                <label>Options (${isTF ? 'choose the correct one' : 'check the correct ones'}):</label>
                ${optionsHTML}
            </div>
        `;

        quizContainer.appendChild(div);
    }

    quizContainer.addEventListener('change', function (e) {
        if (e.target.classList.contains('question-type-select')) {
            const index = e.target.getAttribute('data-index');
            const block = document.getElementById(`quizBlock${index}`);
            const selectedType = e.target.value;
            const optionContainer = block.querySelector('.multiple-section');

            let optionsHTML = '';
            if (selectedType === 'True/False') {
                optionsHTML = `
                    <input type="hidden" name="optionId_${index}_1" value="-1">
                    <input type="hidden" name="optionId_${index}_2" value="-1">

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isCorrect_${index}" id="trueOption_${index}" value="true" required>
                        <label class="form-check-label" for="trueOption_${index}">True</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isCorrect_${index}" id="falseOption_${index}" value="false">
                        <label class="form-check-label" for="falseOption_${index}">False</label>
                    </div>
                `;
            } else {
                optionsHTML = [...Array(4)].map((_, i) => `
                    <div class="input-group mt-2">
                        <input type="hidden" name="optionId_${index}_${i + 1}" value="-1">
                        <div class="input-group-text">
                            <input type="checkbox" name="isCorrect_${index}_${i + 1}" value="true">
                        </div>
                        <input type="text" class="form-control" name="optionText_${index}_${i + 1}" placeholder="Option ${i + 1}" required>
                    </div>
                `).join('');
            }

            optionContainer.innerHTML = `
                <label>Options (${selectedType === 'True/False' ? 'choose the correct one' : 'check the correct ones'}):</label>
                ${optionsHTML}
            `;
        }
    });

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

            const questionTypeSelect = block.querySelector('.question-type-select');
            questionTypeSelect.name = `questionType${index}`;
            questionTypeSelect.id = `questionType${index}`;
            questionTypeSelect.setAttribute("data-index", index);

            const questionType = questionTypeSelect?.value || 'Multiple Choice';

            if (questionType === 'True/False') {
                const trueRadio = block.querySelector(`input[type="radio"][value="true"]`);
                const falseRadio = block.querySelector(`input[type="radio"][value="false"]`);
                const tfLabelTrue = block.querySelector(`label[for^="trueOption_"]`);
                const tfLabelFalse = block.querySelector(`label[for^="falseOption_"]`);

                if (trueRadio) {
                    trueRadio.name = `isCorrect_${index}`;
                    trueRadio.id = `trueOption_${index}`;
                }
                if (falseRadio) {
                    falseRadio.name = `isCorrect_${index}`;
                    falseRadio.id = `falseOption_${index}`;
                }
                if (tfLabelTrue)
                    tfLabelTrue.setAttribute('for', `trueOption_${index}`);
                if (tfLabelFalse)
                    tfLabelFalse.setAttribute('for', `falseOption_${index}`);

                // Also update hidden optionId fields if exist
                const optionId1 = block.querySelector(`input[name^="optionId_"][value="-1"]:nth-of-type(1)`);
                const optionId2 = block.querySelector(`input[name^="optionId_"][value="-1"]:nth-of-type(2)`);
                if (optionId1)
                    optionId1.name = `optionId_${index}_1`;
                if (optionId2)
                    optionId2.name = `optionId_${index}_2`;
            } else {
                for (let i = 0; i < 4; i++) {
                    const checkbox = block.querySelector(`input[name="isCorrect_${index}_${i + 1}"]`);
                    const textInput = block.querySelector(`input[name="optionText_${index}_${i + 1}"]`);
                    const hiddenInput = block.querySelector(`input[name="optionId_${index}_${i + 1}"]`);

                    if (checkbox)
                        checkbox.name = `isCorrect_${index}_${i + 1}`;
                    if (textInput)
                        textInput.name = `optionText_${index}_${i + 1}`;
                    if (hiddenInput)
                        hiddenInput.name = `optionId_${index}_${i + 1}`;
                }
            }

            // Update hidden questionId
            const qidInput = block.querySelector(`input[name^="questionId"]`);
            if (qidInput)
                qidInput.name = `questionId${index}`;
        });

        questionCount = blocks.length;
        questionCountInput.value = questionCount;
    }

    window.deleteQuestion = function (button, questionId) {
        const block = button.closest('.quiz-block');

        if (window.isEditMode && questionId) {
            if (!confirm("Are you sure you want to delete this question?"))
                return;
            fetch('DeleteQuestionServlet?questionId=' + questionId)
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            block.remove();
                            updateAllQuestionIndexes();
                        } else {
                            alert("Failed to delete question.");
                        }
                    })
                    .catch(() => alert("Error deleting question."));
        } else {
            block.remove();
            updateAllQuestionIndexes();
        }
    };

    addQuizBtn.addEventListener("click", () => {
        const currentCount = quizContainer.querySelectorAll('.quiz-block').length;
        createQuizBlock(currentCount);
        questionCount = currentCount + 1;
        questionCountInput.value = questionCount;

        document.getElementById(`quizBlock${currentCount}`)?.scrollIntoView({behavior: 'smooth'});
    });

    if (typeof existingQuestions !== 'undefined' && Array.isArray(existingQuestions) && existingQuestions.length > 0) {
        existingQuestions.forEach((q, idx) => createQuizBlock(idx, q));
        questionCount = existingQuestions.length;
        questionCountInput.value = questionCount;
    }
});


//
//let quizContainer;
//let questionCountInput;
//let addQuizBtn;
//let questionCount = 0;
//
//document.addEventListener("DOMContentLoaded", function () {
//    quizContainer = document.getElementById("quizContainer");
//    addQuizBtn = document.getElementById("addQuizBtn");
//    questionCountInput = document.getElementById("questionCount");
//
//    function createQuizBlock(index, questionData = null) {
//        const div = document.createElement('div');
//        div.className = 'quiz-block border p-3 mb-3 rounded shadow-sm position-relative';
//        div.id = `quizBlock${index}`;
//
//        const questionType = questionData?.type || 'Multiple Choice'; // 'multiple' or 'truefalse'
//        const isTF = questionType === 'True/False';
//
//        // Generate options section
//        let optionsHTML = '';
//
//        if (isTF) {
//            const trueChecked = questionData?.options?.[0]?.isCorrect ? 'checked' : '';
//            const falseChecked = questionData?.options?.[1]?.isCorrect ? 'checked' : '';
//            optionsHTML = `
//            <div class="form-check">
//                <input class="form-check-input" type="radio" name="isCorrect_${index}" id="trueOption_${index}" value="true" ${trueChecked} required>
//                <label class="form-check-label" for="trueOption_${index}">True</label>
//            </div>
//            <div class="form-check">
//                <input class="form-check-input" type="radio" name="isCorrect_${index}" id="falseOption_${index}" value="false" ${falseChecked}>
//                <label class="form-check-label" for="falseOption_${index}">False</label>
//            </div>
//        `;
//        } else {
//            for (let i = 0; i < 4; i++) {
//                const optionId = questionData?.options?.[i]?.optionID ?? -1;
//                const optionText = questionData?.options?.[i]?.optionText || '';
//                const isCorrect = questionData?.options?.[i]?.isCorrect ? 'checked' : '';
//                optionsHTML += `
//                <div class="input-group mt-2">
//                    <input type="hidden" name="optionId_${index}_${i + 1}" value="${optionId}">
//                    <div class="input-group-text">
//                        <input type="checkbox" name="isCorrect_${index}_${i + 1}" value="true" ${isCorrect}>
//                    </div>
//                    <input type="text" class="form-control" name="optionText_${index}_${i + 1}" placeholder="Option ${i + 1}" value="${optionText}" required>
//                </div>
//            `;
//            }
//        }
//
//        div.innerHTML = `
//        <div class="d-flex justify-content-between align-items-start mb-2">
//            <h5>Question ${index + 1}</h5>
//            <div class="ms-auto d-flex align-items-center">
//                <label for="points${index}" class="form-label me-2 mb-0">Points:</label>
//                <input type="number" class="form-control d-inline-block" style="width: 80px;" id="points${index}" name="points${index}" min="1" value="${questionData?.points || 1}" required>
//            </div>
//        </div>
//
//        <button type="button" class="btn btn-sm btn-danger mb-2" onclick="deleteQuestion(this, ${questionData?.questionID !== undefined ? `'${questionData.questionID}'` : 'null'})">Delete Question</button>
//
//        <div class="mb-2">
//            <label for="questionType${index}" class="form-label">Question Type:</label>
//            <select class="form-select question-type-select" name="questionType${index}" id="questionType${index}" data-index="${index}">
//                <option value="Multiple Choice" ${questionType === 'Multiple Choice' ? 'selected' : ''}>Multiple Choice</option>
//                <option value="True/False" ${questionType === 'True/False' ? 'selected' : ''}>True/False</option>
//            </select>
//        </div>
//
//        <div class="mb-3">
//            <input type="hidden" name="questionId${index}" value="${questionData?.questionID ?? -1}">
//            <label for="questionText${index}" class="form-label">Question Text:</label>
//            <input type="text" class="form-control" id="questionText${index}" name="questionText${index}" value="${questionData?.questionText || ''}" required>
//        </div>
//
//        <div class="multiple-section mb-3">
//            <label>Options (${questionType === 'Multiple Choice' ? 'check the correct ones' : 'choose the correct one'}):</label>
//            ${optionsHTML}
//        </div>
//    `;
//
//        quizContainer.appendChild(div);
//    }
//
//    quizContainer.addEventListener('change', function (e) {
//        if (e.target.classList.contains('question-type-select')) {
//            const index = e.target.getAttribute('data-index');
//            const block = document.getElementById(`quizBlock${index}`);
//            const selectedType = e.target.value;
//
//            const optionContainer = block.querySelector('.multiple-section');
//            let optionsHTML = '';
//
//            if (selectedType === 'True/False') {
//                optionsHTML = `
//                <input type="hidden" name="optionId_${index}_1" value="-1">
//                <input type="hidden" name="optionId_${index}_2" value="-1">
//
//                <div class="form-check">
//                    <input class="form-check-input" type="radio" name="isCorrect_${index}" id="trueOption_${index}" value="true" required>
//                    <label class="form-check-label" for="trueOption_${index}">True</label>
//                </div>
//                <div class="form-check">
//                    <input class="form-check-input" type="radio" name="isCorrect_${index}" id="falseOption_${index}" value="false">
//                    <label class="form-check-label" for="falseOption_${index}">False</label>
//                </div>
//            `;
//            } else {
//                optionsHTML = [...Array(4)].map((_, i) => `
//                <div class="input-group mt-2">
//                    <input type="hidden" name="optionId_${index}_${i + 1}" value="-1">
//                    <div class="input-group-text">
//                        <input type="checkbox" name="isCorrect_${index}_${i + 1}" value="true">
//                    </div>
//                    <input type="text" class="form-control" name="optionText_${index}_${i + 1}" placeholder="Option ${i + 1}" required>
//                </div>
//            `).join('');
//            }
//
//            optionContainer.innerHTML = `
//            <label>Options (${selectedType === 'Multiple Choice' ? 'check the correct ones' : 'choose the correct one'}):</label>
//            ${optionsHTML}
//        `;
//        }
//    });
//
//    function updateAllQuestionIndexes() {
//        const blocks = quizContainer.querySelectorAll('.quiz-block');
//
//        blocks.forEach((block, index) => {
//            block.id = `quizBlock${index}`;
//            block.querySelector("h5").innerText = "Question " + (index + 1);
//
//            const pointsInput = block.querySelector('input[type="number"]');
//            pointsInput.name = `points${index}`;
//            pointsInput.id = `points${index}`;
//
//            const pointsLabel = block.querySelector('label[for^="points"]');
//            if (pointsLabel) {
//                pointsLabel.setAttribute('for', `points${index}`);
//            }
//
//            const questionInput = block.querySelector('input[name^="questionText"]');
//            questionInput.name = `questionText${index}`;
//            questionInput.id = `questionText${index}`;
//
//            const questionLabel = block.querySelector('label[for^="questionText"]');
//            if (questionLabel) {
//                questionLabel.setAttribute('for', `questionText${index}`);
//            }
//
//            // Check for question type and update accordingly
//            const questionTypeSelect = block.querySelector('.question-type-select');
//            const questionType = questionTypeSelect?.value || 'Multiple Choice';
//
//            if (questionType === 'True/False') {
//                const trueRadio = block.querySelector(`#trueOption_${index}`);
//                const falseRadio = block.querySelector(`#falseOption_${index}`);
//                const tfLabelTrue = block.querySelector(`label[for="trueOption_${index}"]`);
//                const tfLabelFalse = block.querySelector(`label[for="falseOption_${index}"]`);
//
//                if (trueRadio) {
//                    trueRadio.name = `isCorrect_${index}`;
//                    trueRadio.id = `trueOption_${index}`;
//                }
//                if (falseRadio) {
//                    falseRadio.name = `isCorrect_${index}`;
//                    falseRadio.id = `falseOption_${index}`;
//                }
//                if (tfLabelTrue)
//                    tfLabelTrue.setAttribute('for', `trueOption_${index}`);
//                if (tfLabelFalse)
//                    tfLabelFalse.setAttribute('for', `falseOption_${index}`);
//            } else {
//                for (let i = 0; i < 4; i++) {
//                    const optionCheckbox = block.querySelector(`input[name="isCorrect_${index}_${i + 1}"]`);
//                    if (optionCheckbox) {
//                        optionCheckbox.name = `isCorrect_${index}_${i + 1}`;
//                    }
//                    const optionTextInput = block.querySelector(`input[name="optionText_${index}_${i + 1}"]`);
//                    if (optionTextInput) {
//                        optionTextInput.name = `optionText_${index}_${i + 1}`;
//                    }
//                }
//            }
//        });
//
//        questionCount = blocks.length;
//        questionCountInput.value = questionCount;
//    }
//
//    window.deleteQuestion = function (button, questionId) {
//        const block = button.closest('.quiz-block');
//
//        if (window.isEditMode && questionId) {
//            // Edit mode: delete from DB
//            if (!confirm("Are you sure you want to delete this question?"))
//                return;
//
//            fetch('DeleteQuestionServlet?questionId=' + questionId)
//                    .then(response => response.json())
//                    .then(data => {
//                        if (data.success) {
//                            if (block) {
//                                block.remove();
//                                updateAllQuestionIndexes();
//                            }
//                        } else {
//                            alert("Failed to delete question.");
//                        }
//                    })
//                    .catch(err => {
//                        console.error("Error:", err);
//                        alert("An error occurred.");
//                    });
//        } else {
//            // Create mode: just remove from DOM
//            if (block) {
//                block.remove();
//                updateAllQuestionIndexes();
//            }
//        }
//    };
//
//    addQuizBtn.addEventListener("click", () => {
//        const currentCount = quizContainer.querySelectorAll('.quiz-block').length;
//        createQuizBlock(currentCount);
//        questionCount = currentCount + 1;
//        questionCountInput.value = questionCount;
//
//        const newBlock = document.getElementById(`quizBlock${currentCount}`);
//        if (newBlock)
//            newBlock.scrollIntoView({behavior: 'smooth'});
//    });
//
//    // Load existing questions if any
//    if (typeof existingQuestions !== 'undefined' && Array.isArray(existingQuestions) && existingQuestions.length > 0) {
//        existingQuestions.forEach((q, idx) => createQuizBlock(idx, q));
//        questionCount = existingQuestions.length;
//        questionCountInput.value = questionCount;
//    }
//});
//

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
