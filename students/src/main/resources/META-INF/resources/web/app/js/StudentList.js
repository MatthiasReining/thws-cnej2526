import { LitElement, html, css } from 'https://cdn.jsdelivr.net/npm/lit@3.1.0/+esm';

/**
 * Student List Web Component
 * Displays a list of students with edit and delete options
 */
export class StudentList extends LitElement {
    static properties = {
        students: { type: Array },
        loading: { type: Boolean },
        error: { type: String }
    };

    static styles = css`
        :host {
            display: block;
        }
        
        .student-item {
            transition: transform 0.2s;
        }
        
        .student-item:hover {
            transform: translateX(5px);
        }
        
        .btn-group {
            gap: 0.5rem;
        }
        
        .loading-spinner {
            text-align: center;
            padding: 2rem;
        }
    `;

    constructor() {
        super();
        this.students = [];
        this.loading = false;
        this.error = null;
    }

    render() {
        if (this.loading) {
            return html`
                <div class="loading-spinner">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <p class="mt-2">Loading students...</p>
                </div>
            `;
        }

        if (this.error) {
            return html`
                <div class="alert alert-danger" role="alert">
                    <strong>Error:</strong> ${this.error}
                </div>
            `;
        }

        if (this.students.length === 0) {
            return html`
                <div class="alert alert-info" role="alert">
                    No students found. Add your first student!
                </div>
            `;
        }

        return html`
            <div class="table-responsive">
                <table class="table table-striped table-hover">
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
                            <tr class="student-item">
                                <td>${student.id}</td>
                                <td>${student.firstName}</td>
                                <td>${student.lastName}</td>
                                <td>${student.email}</td>
                                <td>${student.matriculationNumber}</td>
                                <td>${this._formatDate(student.enrollmentDate)}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <button 
                                            class="btn btn-sm btn-primary"
                                            @click=${() => this._onEdit(student)}
                                            title="Edit student">
                                            <i class="bi bi-pencil"></i> Edit
                                        </button>
                                        <button 
                                            class="btn btn-sm btn-danger"
                                            @click=${() => this._onDelete(student)}
                                            title="Delete student">
                                            <i class="bi bi-trash"></i> Delete
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        `)}
                    </tbody>
                </table>
            </div>
        `;
    }

    _formatDate(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    }

    _onEdit(student) {
        this.dispatchEvent(new CustomEvent('edit-student', {
            detail: { student },
            bubbles: true,
            composed: true
        }));
    }

    _onDelete(student) {
        if (confirm(`Are you sure you want to delete ${student.firstName} ${student.lastName}?`)) {
            this.dispatchEvent(new CustomEvent('delete-student', {
                detail: { student },
                bubbles: true,
                composed: true
            }));
        }
    }
}

customElements.define('student-list', StudentList);
