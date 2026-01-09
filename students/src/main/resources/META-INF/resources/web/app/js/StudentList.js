import { html, render } from 'lit';

/**
 * StudentList - Component for displaying and managing the list of students
 */
export class StudentList {
    constructor(containerId, onEdit, onDelete) {
        this.container = document.getElementById(containerId);
        this.students = [];
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    /**
     * Set students data and render
     * @param {Array} students - Array of student objects
     */
    setStudents(students) {
        this.students = students;
        this.render();
    }

    /**
     * Format date for display
     * @param {string} dateString - ISO date string
     * @returns {string} Formatted date
     */
    formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    }

    /**
     * Handle edit button click
     * @param {number} id - Student ID
     */
    handleEdit(id) {
        if (this.onEdit) {
            this.onEdit(id);
        }
    }

    /**
     * Handle delete button click
     * @param {number} id - Student ID
     * @param {string} name - Student name for confirmation
     */
    handleDelete(id, name) {
        if (confirm(`Are you sure you want to delete student ${name}?`)) {
            if (this.onDelete) {
                this.onDelete(id);
            }
        }
    }

    /**
     * Render the student list using Lit
     */
    render() {
        const template = html`
            <div class="card">
                <div class="card-body">
                    ${this.students.length === 0 ? html`
                        <div class="alert alert-info" role="alert">
                            No students found. Click "Add New Student" to create one.
                        </div>
                    ` : html`
                        <div class="table-responsive">
                            <table class="table table-hover table-striped">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Email</th>
                                        <th>Matriculation Number</th>
                                        <th>Enrollment Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${this.students.map(student => html`
                                        <tr>
                                            <td>${student.id}</td>
                                            <td>${student.firstName}</td>
                                            <td>${student.lastName}</td>
                                            <td>${student.email}</td>
                                            <td>${student.matriculationNumber}</td>
                                            <td>${this.formatDate(student.enrollmentDate)}</td>
                                            <td>
                                                <button 
                                                    class="btn btn-sm btn-primary me-1" 
                                                    @click=${() => this.handleEdit(student.id)}
                                                    title="Edit">
                                                    Edit
                                                </button>
                                                <button 
                                                    class="btn btn-sm btn-danger" 
                                                    @click=${() => this.handleDelete(student.id, `${student.firstName} ${student.lastName}`)}
                                                    title="Delete">
                                                    Delete
                                                </button>
                                            </td>
                                        </tr>
                                    `)}
                                </tbody>
                            </table>
                        </div>
                        <div class="mt-3">
                            <small class="text-muted">Total students: ${this.students.length}</small>
                        </div>
                    `}
                </div>
            </div>
        `;

        render(template, this.container);
    }

    /**
     * Show loading state
     */
    showLoading() {
        const template = html`
            <div class="card">
                <div class="card-body text-center">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <p class="mt-2">Loading students...</p>
                </div>
            </div>
        `;
        render(template, this.container);
    }

    /**
     * Show error message
     * @param {string} message - Error message
     */
    showError(message) {
        const template = html`
            <div class="card">
                <div class="card-body">
                    <div class="alert alert-danger" role="alert">
                        <strong>Error:</strong> ${message}
                    </div>
                </div>
            </div>
        `;
        render(template, this.container);
    }
}
