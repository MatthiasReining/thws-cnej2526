import { LitElement, html, css } from 'https://cdn.jsdelivr.net/npm/lit@3.1.0/+esm';
import { StudentService } from './StudentService.js';
import './StudentList.js';
import './StudentForm.js';

/**
 * Main Application Component
 * Manages the overall state and orchestrates student CRUD operations
 */
export class App extends LitElement {
    static properties = {
        students: { type: Array },
        loading: { type: Boolean },
        error: { type: String },
        showForm: { type: Boolean },
        currentStudent: { type: Object },
        formMode: { type: String }
    };

    static styles = css`
        :host {
            display: block;
        }
        
        .app-header {
            margin-bottom: 2rem;
        }
        
        .actions-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 0.5rem;
        }
        
        .toast-container {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1050;
        }
        
        .toast {
            min-width: 300px;
        }
    `;

    constructor() {
        super();
        this.studentService = new StudentService();
        this.students = [];
        this.loading = false;
        this.error = null;
        this.showForm = false;
        this.currentStudent = null;
        this.formMode = 'create';
    }

    connectedCallback() {
        super.connectedCallback();
        this.loadStudents();
    }

    render() {
        return html`
            <div class="app-container">
                <div class="actions-bar">
                    <div>
                        <h5 class="mb-0">Students Overview</h5>
                        <small class="text-muted">${this.students.length} student(s) total</small>
                    </div>
                    <div>
                        <button 
                            class="btn btn-success"
                            @click=${this._handleAddNew}
                            ?disabled=${this.loading}>
                            <i class="bi bi-plus-circle"></i> Add New Student
                        </button>
                        <button 
                            class="btn btn-secondary ms-2"
                            @click=${this.loadStudents}
                            ?disabled=${this.loading}>
                            <i class="bi bi-arrow-clockwise"></i> Refresh
                        </button>
                    </div>
                </div>

                ${this._renderToast()}
                
                <student-form
                    .student=${this.currentStudent}
                    .mode=${this.formMode}
                    .visible=${this.showForm}
                    @create-student=${this._handleCreate}
                    @update-student=${this._handleUpdate}
                    @cancel-form=${this._handleCancelForm}>
                </student-form>
                
                <student-list
                    .students=${this.students}
                    .loading=${this.loading}
                    .error=${this.error}
                    @edit-student=${this._handleEdit}
                    @delete-student=${this._handleDelete}>
                </student-list>
            </div>
        `;
    }

    _renderToast() {
        if (!this.error) return html``;

        return html`
            <div class="toast-container">
                <div class="toast show" role="alert">
                    <div class="toast-header bg-danger text-white">
                        <strong class="me-auto">Error</strong>
                        <button type="button" class="btn-close btn-close-white" @click=${this._clearError}></button>
                    </div>
                    <div class="toast-body">
                        ${this.error}
                    </div>
                </div>
            </div>
        `;
    }

    async loadStudents() {
        this.loading = true;
        this.error = null;

        try {
            this.students = await this.studentService.getAllStudents();
        } catch (error) {
            this.error = `Failed to load students: ${error.message}`;
            console.error('Error loading students:', error);
        } finally {
            this.loading = false;
        }
    }

    _handleAddNew() {
        this.currentStudent = null;
        this.formMode = 'create';
        this.showForm = true;
        this._scrollToTop();
    }

    _handleEdit(e) {
        this.currentStudent = e.detail.student;
        this.formMode = 'edit';
        this.showForm = true;
        this._scrollToTop();
    }

    async _handleCreate(e) {
        const studentData = e.detail.student;
        const form = this.shadowRoot.querySelector('student-form');

        if (form) {
            form.submitting = true;
        }

        try {
            await this.studentService.createStudent(studentData);
            await this.loadStudents();
            this.showForm = false;
            this._showSuccessMessage('Student created successfully!');
        } catch (error) {
            this.error = `Failed to create student: ${error.message}`;
        } finally {
            if (form) {
                form.submitting = false;
            }
        }
    }

    async _handleUpdate(e) {
        const studentData = e.detail.student;
        const form = this.shadowRoot.querySelector('student-form');

        if (form) {
            form.submitting = true;
        }

        try {
            await this.studentService.updateStudent(studentData.id, studentData);
            await this.loadStudents();
            this.showForm = false;
            this._showSuccessMessage('Student updated successfully!');
        } catch (error) {
            this.error = `Failed to update student: ${error.message}`;
        } finally {
            if (form) {
                form.submitting = false;
            }
        }
    }

    async _handleDelete(e) {
        const student = e.detail.student;

        try {
            await this.studentService.deleteStudent(student.id);
            await this.loadStudents();
            this._showSuccessMessage('Student deleted successfully!');
        } catch (error) {
            this.error = `Failed to delete student: ${error.message}`;
        }
    }

    _handleCancelForm() {
        this.showForm = false;
        this.currentStudent = null;
    }

    _clearError() {
        this.error = null;
    }

    _showSuccessMessage(message) {
        // You could implement a success toast similar to the error toast
        console.log('Success:', message);
        // For now, just clear any existing errors
        this.error = null;
    }

    _scrollToTop() {
        this.shadowRoot.querySelector('.app-container')?.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
    }
}

customElements.define('student-app', App);
