/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    let questionCount = 0;
    const quizContainer = document.getElementById("quizContainer");
    const addQuizBtn = document.getElementById("addQuizBtn");
    const questionCountInput = document.getElementById("questionCount");

    function createQuizBlock(index) {
        const div = document.createElement('div');
        div.className = 'quiz-block border p-3 mb-3 rounded shadow-sm position-relative';
        div.id = `quizBlock${index}`;

        let optionsHTML = '';
        for (let i = 1; i <= 4; i++) {
            optionsHTML += `
        <div class="input-group mt-2">
            <div class="input-group-text">
                <input type="checkbox" name="isCorrect_${index}_${i}" value="true" aria-label="Checkbox for option ${i}">
            </div>
            <input type="text" class="form-control" name="optionText_${index}_${i}" placeholder="Option ${i}" required>
        </div>`;
        }

        div.innerHTML = `
        <div class="d-flex justify-content-between align-items-start">
            <h5>Question ${index + 1}</h5>
            <div class="ms-auto">
                <label for="points${index}" class="form-label me-2">Points:</label>
                <input type="number" class="form-control d-inline-block" style="width: 80px;" id="points${index}" name="points${index}" min="1" value="1" required>
            </div>
        </div>
        
        <button type="button" class="btn btn-sm btn-danger mb-2" onclick="deleteQuestion(this)">Delete</button>

   

        <div class="mb-3">
            <label for="questionText${index}" class="form-label">Question Text:</label>
            <input type="text" class="form-control" id="questionText${index}" name="questionText${index}" required>
        </div>

        <div class="multiple-section mb-3">
            <label>Options (check the correct ones):</label>
            ${optionsHTML}
        </div>`;

        quizContainer.appendChild(div);
    }

    addQuizBtn.addEventListener("click", () => {
        createQuizBlock(questionCount);
        questionCount++;
        questionCountInput.value = questionCount;
        addQuizBtn.scrollIntoView({behavior: 'smooth'});
    });
});

function deleteQuestion(button) {
    const block = button.closest('.quiz-block');
    if (block) {
        block.remove();
        updateAllQuestionIndexes();
    }
}


function updateAllQuestionIndexes() {
    const quizContainer = document.getElementById("quizContainer");
    const questionCountInput = document.getElementById("questionCount");
    const blocks = quizContainer.querySelectorAll('.quiz-block');

    blocks.forEach((block, index) => {
        block.querySelector("h5").innerText = "Question " + (index + 1);

        // Points
        const pointsInput = block.querySelector('input[type="number"]');
        pointsInput.name = `points${index}`;
        pointsInput.id = `points${index}`;

        const pointsLabel = block.querySelector('label[for^="points"]');
        if (pointsLabel)
            pointsLabel.setAttribute('for', `points${index}`);

        // Question Text
        const questionInput = block.querySelector('input[type="text"][name^="questionText"]');
        questionInput.name = `questionText${index}`;
        questionInput.id = `questionText${index}`;

        const questionLabel = block.querySelector('label[for^="questionText"]');
        if (questionLabel)
            questionLabel.setAttribute('for', `questionText${index}`);

        // Options
        const optionInputs = block.querySelectorAll('.input-group');
        optionInputs.forEach((group, optIndex) => {
            const optInput = group.querySelector('input[type="text"]');
            const checkbox = group.querySelector('input[type="checkbox"]');

            optInput.name = `optionText_${index}_${optIndex + 1}`;
            checkbox.name = `isCorrect_${index}_${optIndex + 1}`;
        });

        // Update delete button
        const deleteBtn = block.querySelector('button.btn-danger');
        deleteBtn.setAttribute('onclick', `deleteQuestion(this)`);
    });

    questionCountInput.value = blocks.length;
}




//function renumberQuestions() {
//    const blocks = document.querySelectorAll('.quiz-block');
//    questionCount = blocks.length;
//    questionCountInput.value = questionCount;
//    blocks.forEach((block, i) => {
//        block.querySelector("h5").innerText = "Question " + (i + 1);
//        block.id = `quizBlock${i}`;
//
//        // Update the "Points" label and input name/id
//        const pointsInput = block.querySelector('input[id^="points"]');
//        pointsInput.setAttribute('id', `points${i}`);
//        pointsInput.setAttribute('name', `points${i}`);
//
//        const pointsLabel = block.querySelector('label[for^="points"]');
//        pointsLabel.setAttribute('for', `points${i}`);
//
//        const btn = block.querySelector('button.btn-danger');
//        btn.setAttribute('onclick', `deleteQuestion(${i})`);
//
//        block.querySelector('label[for^="questionText"]').setAttribute('for', `questionText${i}`);
//        block.querySelector('input[id^="questionText"]').setAttribute('id', `questionText${i}`);
//        block.querySelector('input[name^="questionText"]').setAttribute('name', `questionText${i}`);
//
//        for (let j = 1; j <= 4; j++) {
//            block.querySelector(`input[name="optionText_${i}_${j}"]`)?.setAttribute('name', `optionText_${i}_${j}`);
//            block.querySelector(`input[name="isCorrect_${i}_${j}"]`)?.setAttribute('name', `isCorrect_${i}_${j}`);
//        }
//    });
//}
//    <button type="button" class="btn btn-sm btn-danger mb-2" onclick="deleteQuestion(${index})">Delete</button>