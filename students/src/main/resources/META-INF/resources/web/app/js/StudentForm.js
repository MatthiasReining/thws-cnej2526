/**
 * StudentForm - Component for handling student form interactions
 */
export class StudentForm {
    constructor(formId, modalId, onSave) {
        this.form = document.getElementById(formId);
        this.modal = new bootstrap.Modal(document.getElementById(modalId));
        this.modalElement = document.getElementById(modalId);
        this.onSave = onSave;
        this.isEditMode = false;
        this.currentStudentId = null;

        this.initializeEventListeners();
    }

    /**
     * Initialize form event listeners
     */
    initializeEventListeners() {
        // Save button click
        document.getElementById('btnSaveStudent').addEventListener('click', () => {
            this.handleSubmit();
        });

        // Form submit (Enter key)
        this.form.addEventListener('submit', (e) => {
            e.preventDefault();
            this.handleSubmit();
        });

        // Reset form when modal is closed
        this.modalElement.addEventListener('hidden.bs.modal', () => {
            this.resetForm();
        });
    }

    /**
     * Show form for creating a new student
     */
    showCreateForm() {
        this.isEditMode = false;
        this.currentStudentId = null;
        this.resetForm();
        document.getElementById('studentModalLabel').textContent = 'Add Student';
        this.modal.show();
    }

    /**
     * Show form for editing an existing student
     * @param {Object} student - Student data
     */
    showEditForm(student) {
        this.isEditMode = true;
        this.currentStudentId = student.id;
        this.resetForm();
        document.getElementById('studentModalLabel').textContent = 'Edit Student';

        // Populate form with student data
        document.getElementById('studentId').value = student.id;
        document.getElementById('firstName').value = student.firstName || '';
        document.getElementById('lastName').value = student.lastName || '';
        document.getElementById('email').value = student.email || '';
        document.getElementById('matriculationNumber').value = student.matriculationNumber || '';
        document.getElementById('enrollmentDate').value = student.enrollmentDate || '';

        this.modal.show();
    }

    /**
     * Handle form submission
     */
    handleSubmit() {
        // Validate form
        if (!this.form.checkValidity()) {
            this.form.classList.add('was-validated');
            return;
        }

        // Get form data
        const studentData = this.getFormData();

        // Call save callback
        if (this.onSave) {
            this.onSave(this.isEditMode, this.currentStudentId, studentData);
        }

        // Hide modal
        this.modal.hide();
    }

    /**
     * Get form data as object
     * @returns {Object} Student data
     */
    getFormData() {
        const data = {
            firstName: document.getElementById('firstName').value.trim(),
            lastName: document.getElementById('lastName').value.trim(),
            email: document.getElementById('email').value.trim(),
            matriculationNumber: document.getElementById('matriculationNumber').value.trim(),
            enrollmentDate: document.getElementById('enrollmentDate').value
        };

        return data;
    }

    /**
     * Reset form to initial state
     */
    resetForm() {
        this.form.reset();
        this.form.classList.remove('was-validated');
        this.isEditMode = false;
        this.currentStudentId = null;
    }

    /**
     * Disable form while processing
     */
    disableForm() {
        const inputs = this.form.querySelectorAll('input');
        inputs.forEach(input => input.disabled = true);
        document.getElementById('btnSaveStudent').disabled = true;
    }

    /**
     * Enable form after processing
     */
    enableForm() {
        const inputs = this.form.querySelectorAll('input');
        inputs.forEach(input => input.disabled = false);
        document.getElementById('btnSaveStudent').disabled = false;
    }
}
