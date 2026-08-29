 // Validación en cliente: contraseñas coinciden antes de enviar
        document.querySelector('form').addEventListener('submit', function (e) {
            const pass = document.getElementById('inputContrasena').value;
            const confirm = document.getElementById('inputConfirmar').value;
            const errorDiv = document.getElementById('errorConfirmar');
            if (pass !== confirm) {
                e.preventDefault();
                errorDiv.style.display = 'block';
                document.getElementById('inputConfirmar').classList.add('is-invalid');
            } else {
                errorDiv.style.display = 'none';
                document.getElementById('inputConfirmar').classList.remove('is-invalid');
            }
        });

        document.getElementById('inputConfirmar').addEventListener('input', function () {
            const pass = document.getElementById('inputContrasena').value;
            const errorDiv = document.getElementById('errorConfirmar');
            if (this.value && this.value !== pass) {
                errorDiv.style.display = 'block';
                this.classList.add('is-invalid');
            } else {
                errorDiv.style.display = 'none';
                this.classList.remove('is-invalid');
            }
        });