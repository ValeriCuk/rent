let tempBuilding = {
    banner: null,
    projectPhoto1: null,
    projectPhoto2: null,
    projectPhoto3: null,
    infraPhoto1: null,
    infraPhoto2: null,
    infraPhoto3: null,
    apartmentPhoto1: null,
    apartmentPhoto2: null,
    apartmentPhoto3: null,
    panoramaPhoto: null,
    title:null,
    sortOrder:null,
    address:null,
    description:null,
    longitude:null,
    latitude:null,
    locationDescription:null,
    infraDescription:null,
    apartmentDescription:null
}

function initializeTempBuildingFromDOM() {
    document.querySelectorAll(".text-input").forEach(input => {
        const field = input.dataset.field;
        if (!field) return;
        const currentValue = input.value;
        console.log(`${field} - ${currentValue}`)
        if (tempBuilding[field] === null || tempBuilding[field] === undefined) {
            tempBuilding[field] = currentValue;
        }
    });
}

function storeTemporaryData() {
    inputPreviewRender();
    restorePreviews();
    bindTextInputs();
    restoreTextInputs();
}

function inputPreviewRender() {
    document.querySelectorAll(".preview-input").forEach(input => {
        input.addEventListener("change", function (event) {
            const file = event.target.files[0];
            const field = input.dataset.field;
            const targetId = this.dataset.target;
            const previewImg = document.getElementById(targetId);

            if (!previewImg) return;

            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    previewImg.src = e.target.result;
                };
                reader.readAsDataURL(file);
                tempBuilding[field] = file;

            } else {
                previewImg.src = "/img/proxy-image.png";
                tempBuilding[field] = null;
            }
        });
    });
}

function restorePreviews() {
    Object.entries(tempBuilding).forEach(([field, file]) => {
        if (!file) return;

        const input = document.querySelector(`.preview-input[data-field="${field}"]`);
        const targetId = input?.dataset.target;
        const previewImg = document.getElementById(targetId);

        if (previewImg) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImg.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
}

function bindTextInputs() {
    document.querySelectorAll(".text-input").forEach(input => {
        const field = input.dataset.field;
        if (!field) return;

        input.addEventListener("input", () => {
            tempBuilding[field] = input.value;
            console.log(`bindTextInputs ${field} -  -> ${input.value}`)
        });
    });
}

function restoreTextInputs() {
    Object.entries(tempBuilding).forEach(([field, value]) => {
        console.log(`restoreTextInputs ${field} - ++ -> ${value}`)
        const input = document.querySelector(`.text-input[data-field="${field}"]`);
        if (input) input.value = value;
    });
}
