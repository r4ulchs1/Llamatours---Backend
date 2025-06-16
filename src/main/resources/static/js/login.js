document.querySelectorAll('.toggle-password').forEach(function(eyeIcon) {
    eyeIcon.addEventListener('click', function() {
        var passwordInput = this.previousElementSibling; // Encuentra el input antes del icono
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            this.textContent = "👁️";
        } else {
            passwordInput.type = "password";
            this.textContent = "👁️‍🗨️";
        }
    });
});