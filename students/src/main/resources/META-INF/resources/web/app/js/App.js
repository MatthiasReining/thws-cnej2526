import { StudentService } from './StudentService.js';
import { StudentList } from './StudentList.js';
import { StudentForm } from './StudentForm.js';

/**
 * App - Main application class
 * Coordinates interactions between StudentService, StudentList, and StudentForm
 */
class App {
    constructor() {
        this.studentService = new StudentService('/students');
        this.studentList = new StudentList(
            'studentListContainer',
            (id) => this.handleEdit(id),
            (id) => this.handleDelete(id)
        );
        this.studentForm = new StudentForm(
            'studentForm',
            'studentModal',
            (isEdit, id, data) => this.handleSave(isEdit, id, data)
        );

        this.initializeEventListeners();
        this.loadStudents();
    }

    /**
     * Initialize global event listeners
     */
    initializeEventListeners() {
        document.getElementById('btnAddStudent').addEventListener('click', () => {
            this.studentForm.showCreateForm();
        });
    }

    /**
     * Load all students from the backend
     */
    async loadStudents() {
        try {
            this.studentList.showLoading();
            const students = await this.studentService.getAllStudents();
            this.studentList.setStudents(students);
        } catch (error) {
            this.studentList.showError('Failed to load students. Please try again.');
            this.showAlert('Failed to load students', 'danger');
            console.error('Error loading students:', error);
        }
    }

    /**
     * Handle edit student action
     * @param {number} id - Student ID
     */
    async handleEdit(id) {
        try {
            const student = await this.studentService.getStudentById(id);
            this.studentForm.showEditForm(student);
        } catch (error) {
            this.showAlert(`Failed to load student with ID ${id}`, 'danger');
            console.error('Error loading student:', error);
        }
    }

    /**
     * Handle delete student action
     * @param {number} id - Student ID
     */
    async handleDelete(id) {
        try {
            await this.studentService.deleteStudent(id);
            this.showAlert('Student deleted successfully', 'success');
            await this.loadStudents();
        } catch (error) {
            this.showAlert('Failed to delete student', 'danger');
            console.error('Error deleting student:', error);
        }
    }

    /**
     * Handle save student action (create or update)
     * @param {boolean} isEdit - True if editing, false if creating
     * @param {number} id - Student ID (for edit)
     * @param {Object} data - Student data
     */
    async handleSave(isEdit, id, data) {
        try {
            if (isEdit) {
                await this.studentService.updateStudent(id, data);
                this.showAlert('Student updated successfully', 'success');
            } else {
                await this.studentService.createStudent(data);
                this.showAlert('Student created successfully', 'success');
            }
            await this.loadStudents();
        } catch (error) {
            this.showAlert(
                isEdit ? 'Failed to update student' : 'Failed to create student',
                'danger'
            );
            console.error('Error saving student:', error);
        }
    }

    /**
     * Show alert message
     * @param {string} message - Alert message
     * @param {string} type - Alert type (success, danger, warning, info)
     */
    showAlert(message, type = 'info') {
        const alertContainer = document.getElementById('alertContainer');
        const alertId = `alert-${Date.now()}`;

        const alertHtml = `
            <div id="${alertId}" class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;

        alertContainer.insertAdjacentHTML('beforeend', alertHtml);

        // Auto-dismiss after 5 seconds
        setTimeout(() => {
            const alertElement = document.getElementById(alertId);
            if (alertElement) {
                const bsAlert = new bootstrap.Alert(alertElement);
                bsAlert.close();
            }
        }, 5000);
    }
}

// Initialize the application when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    new App();
});
