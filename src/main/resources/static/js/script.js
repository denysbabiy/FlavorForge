var language = 'en';
var translations = {
    en: {
        vegetarianLabel: 'Vegetarian',
        providedIngredientsLabel: 'Include only provided ingredients',
        recipePlaceHolder: 'Recipe will appear here!',
        companyName: 'Flavor Forge',
        dishType: 'Dish Type:',
        ingredients: 'Ingredients:',
        addIngredient: 'Add Ingredient',
        generateRecipe: 'Generate Recipe',
        recipe: 'Recipe',
        instructions: 'Instructions'
    },
    ua: {
        vegetarianLabel: 'Вегетаріанська',
        providedIngredientsLabel: 'Тільки з вказаних продуктів',
        recipePlaceHolder: 'Рецепт буде тут!',
        companyName: 'Кузня Смаку',
        dishType: 'Тип страви:',
        ingredients: 'Інгредієнти:',
        addIngredient: 'Додати інгредієнт',
        generateRecipe: 'Створити рецепт',
        recipe: 'Рецепт',
        instructions: 'Інструкція'
    }
};

function switchLanguage() {
    language = document.getElementById('languageSwitch').value;
    document.getElementById('recipePlaceHolder').innerText = translations[language].recipePlaceHolder;
    document.getElementById('companyNameLabel').innerText = translations[language].companyName;
    document.getElementById('dishTypeLabel').innerText = translations[language].dishType;
    document.getElementById('ingredientsLabel').innerText = translations[language].ingredients;
    document.getElementById('vegetarianLabel').innerText = translations[language].vegetarianLabel;
    document.getElementById('providedIngredientsLabel').innerText = translations[language].providedIngredientsLabel;
    document.getElementById('addIngredientButton').innerText = translations[language].addIngredient;
    document.getElementById('generateRecipeButton').innerText = translations[language].generateRecipe;
    document.getElementById('recipeTitle').innerText = translations[language].recipe;
    document.getElementById('ingredientsTitle').innerText = translations[language].ingredients;
    document.getElementById('instructionsTitle').innerText = translations[language].instructions;
}

function generateRecipe() {
    var dishType = document.getElementById('dishType').value;
    var ingredients = Array.from(document.getElementsByClassName('ingredient')).map(input => input.value);
    var isVegetarian = document.getElementById('vegetarian').checked;
    var onlyProvidedIngredients = document.getElementById('providedIngredients').checked;

    var recipeRequest = {
        dishType: dishType,
        ingredients: ingredients,
        isVegetarian: isVegetarian,
        onlyProvidedIngredients: onlyProvidedIngredients
    };

    document.getElementById('recipePlaceHolder').style.display  = 'none';
    document.getElementById('loader').style.display = 'block';

    fetch('/v1/generate/recipe?lang=' + language, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(recipeRequest)
    })
        .then(response => response.json())
        .then(recipe => {
            document.getElementById('loader').style.display = 'none'; // Hide loading animation
            document.getElementById('title').innerText = recipe.title;
            document.getElementById('summary').innerText = recipe.summary;
            document.getElementById('instructions').innerText = recipe.instructions.join('\n');

            // Display the headers
            document.getElementById('recipeTitle').innerText = translations[language].recipe;
            document.getElementById('ingredientsTitle').innerText = translations[language].ingredients;
            document.getElementById('instructionsTitle').innerText = translations[language].instructions;
            document.getElementById('recipeTitle').style.display = 'block';
            document.getElementById('ingredientsTitle').style.display = 'block';
            document.getElementById('instructionsTitle').style.display = 'block';
            document.getElementById('title').style.display = 'block';
            document.getElementById('summary').style.display = 'block';
            document.getElementById('instructions').style.display = 'block';

            var ingredientsList = document.getElementById('recipeIngredients');
            ingredientsList.innerHTML = '';
            recipe.ingredients.forEach(ingredient => {
                var li = document.createElement('li');
                li.innerText = ingredient.name + ': ' + ingredient.amount;
                ingredientsList.appendChild(li);
            });
            document.getElementById('recipeIngredients').style.display = 'block';

            // Make a request to /image
            var imageRequest = {
                description: recipe.summary,
                numberOfImages: 1
            };

            document.getElementById('loader2').style.display = 'block';

            fetch('/v1/generate/image', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(imageRequest)
            })
                .then(response => response.json())
                .then(image => {
                    var imgContainer = document.getElementById('imgContainer');
                    var oldImg = imgContainer.getElementsByTagName('img')[0];
                    if (oldImg) {
                        imgContainer.removeChild(oldImg);
                    }

                    var img = document.createElement('img');
                    img.src = image.imageUrl;
                    img.className = 'recipe-image';
                    imgContainer.appendChild(img);
                    document.getElementById('loader2').style.display = 'none'; // Hide loading animation
                });
        });
}

function generateRecipeByPhoto() {
    var dishType = document.getElementById('dishType2').value;
    var photoUpload = document.getElementById('photoUpload').files[0];
    var lang = document.getElementById('languageSwitch').value;

    if (!photoUpload) {
        alert('Please upload a photo.');
        return;
    }

    var formData = new FormData();
    formData.append('image', photoUpload);
    formData.append('lang', lang);
    formData.append('dishType', dishType);

    document.getElementById('recipePlaceHolder').style.display  = 'none';
    document.getElementById('loader').style.display = 'block';

    fetch('/v1/generate/recipeByImage', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(recipe => {
            document.getElementById('loader').style.display = 'none';
            document.getElementById('title').innerText = recipe.title;
            document.getElementById('summary').innerText = recipe.summary;
            document.getElementById('instructions').innerText = recipe.instructions.join('\n');

            // Display the headers
            document.getElementById('recipeTitle').innerText = translations[lang].recipe;
            document.getElementById('ingredientsTitle').innerText = translations[lang].ingredients;
            document.getElementById('instructionsTitle').innerText = translations[lang].instructions;
            document.getElementById('recipeTitle').style.display = 'block';
            document.getElementById('ingredientsTitle').style.display = 'block';
            document.getElementById('instructionsTitle').style.display = 'block';
            document.getElementById('title').style.display = 'block';
            document.getElementById('summary').style.display = 'block';
            document.getElementById('instructions').style.display = 'block';

            var ingredientsList = document.getElementById('recipeIngredients');
            ingredientsList.innerHTML = '';
            recipe.ingredients.forEach(ingredient => {
                var li = document.createElement('li');
                li.innerText = ingredient.name + ': ' + ingredient.amount;
                ingredientsList.appendChild(li);
            });
            document.getElementById('recipeIngredients').style.display = 'block';
            // Make a request to /image
            var imageRequest = {
                description: recipe.summary,
                numberOfImages: 1
            };

            document.getElementById('loader2').style.display = 'block';

            fetch('/v1/generate/image', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(imageRequest)
            })
                .then(response => response.json())
                .then(image => {
                    var imgContainer = document.getElementById('imgContainer');
                    var oldImg = imgContainer.getElementsByTagName('img')[0];
                    if (oldImg) {
                        imgContainer.removeChild(oldImg);
                    }

                    var img = document.createElement('img');
                    img.src = image.imageUrl;
                    img.className = 'recipe-image';
                    imgContainer.appendChild(img);
                    document.getElementById('loader2').style.display = 'none'; // Hide loading animation
                });
        })
        .catch(error => {
            document.getElementById('loader').style.display = 'none';
            console.error('Error:', error);
        });
}

function addIngredient() {
    var ingredientsDiv = document.getElementById('ingredients');
    var newDiv = document.createElement('div');
    newDiv.className = 'input-group mb-3';
    var newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.className = 'ingredient form-control';
    var deletingButton = document.createElement('button');
    deletingButton.innerText = 'X';
    deletingButton.className = 'btn btn-outline-secondary';
    deletingButton.onclick = function () {
        ingredientsDiv.removeChild(newDiv);
    };
    newDiv.appendChild(newInput);
    newDiv.appendChild(deletingButton);
    ingredientsDiv.appendChild(newDiv);
}

function toggleForms() {
    const toggleSwitch = document.getElementById('toggleSwitch');
    const textForm = document.getElementById('textForm');
    const photoForm = document.getElementById('photoForm');
    const toggleLabel = document.getElementById('toggleLabel');

    if (toggleSwitch.checked) {
        textForm.style.display = 'none';
        photoForm.style.display = 'block';
        toggleLabel.textContent = 'Generate by Photo';
    } else {
        textForm.style.display = 'block';
        photoForm.style.display = 'none';
        toggleLabel.textContent = 'Generate by Text';
    }
}
