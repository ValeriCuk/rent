const inputPreviews = document.querySelectorAll(".preview-input");

if (inputPreviews.length > 0) {
    console.log('inputPreviews work')
    inputPreviews.forEach(input => {
        input.addEventListener("change", function (event) {
            const file = event.target.files[0];
            const targetId = this.dataset.target;
            const previewImg = document.getElementById(targetId);

            if (!previewImg) return;

            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    previewImg.src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                console.log('uses proxy image')
                previewImg.src = "/img/proxy-image.png";
            }
        });
    });
} else {
    console.warn('inputPreviews length is 0. Not found !')
}