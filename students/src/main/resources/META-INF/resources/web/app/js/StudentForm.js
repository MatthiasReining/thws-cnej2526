import { LitElement, html, css } from 'https://cdn.jsdelivr.net/npm/lit@3.1.0/+esm';

/**
 * Student Form Web Component
 * Handles creating and editing students
 */
export class StudentForm extends LitElement {
    static properties = {
        student: { type: Object },
        mode: { type: String }, // 'create' or 'edit'
        visible: { type: Boolean },
        submitting: { type: Boolean }
    };

    static styles = css`
        :host {
            display: block;
        }
        
        .form-container {
            background: #f8f9fa;
            padding: 2rem;
            border-radius: 0.5rem;
            margin-bottom: 2rem;
        }
        
        .form-header {
            margin-bottom: 1.5rem;
        }
        
        .form-actions {
            display: flex;
            gap: 1rem;
            margin-top: 1.5rem;
        }
    `;

    constructor() {
        super();
        this.student = null;
        this.mode = 'create';
        this.visible = false;
        this.submitting = false;
    }

    render() {
        if (!this.visible) {
            return html``;
        }

        const isEdit = this.mode === 'edit';
        const title = isEdit ? 'Edit Student' : 'Create New Student';
        const submitText = isEdit ? 'Update Student' : 'Create Student';

        return html`
            <div class="form-container">
                <div class="form-header">
                    <h3>${title}</h3>
                </div>
                
                <form @submit=${this._handleSubmit}>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="firstName" class="form-label">First Name *</label>
                            <input 
                                type="text" 
                                class="form-control" 
                                id="firstName" 
                                name="firstName"
                                .value=${this.student?.firstName || ''}
                                required
                                ?disabled=${this.submitting}>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="lastName" class="form-label">Last Name *</label>
                            <input 
                                type="text" 
                                class="form-control" 
                                id="lastName" 
                                name="lastName"
                                .value=${this.student?.lastName || ''}
                                required
                                ?disabled=${this.submitting}>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label">Email *</label>
                            <input 
                                type="email" 
                                class="form-control" 
                                id="email" 
                                name="email"
                                .value=${this.student?.email || ''}
                                required
                                ?disabled=${this.submitting}>
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="matriculationNumber" class="form-label">Matriculation Number *</label>
                            <input 
                                type="text" 
                                class="form-control" 
                                id="matriculationNumber" 
                                name="matriculationNumber"
                                .value=${this.student?.matriculationNumber || ''}
                                required
                                ?disabled=${this.submitting}>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="enrollmentDate" class="form-label">Enrollment Date *</label>
                        <input 
                            type="date" 
                            class="form-control" 
                            id="enrollmentDate" 
                            name="enrollmentDate"
                            .value=${this._formatDateForInput(this.student?.enrollmentDate)}
                            required
                            ?disabled=${this.submitting}>
                    </div>
                    
                    <div class="form-actions">
                        <button 
                            type="submit" 
                            class="btn btn-primary"
                            ?disabled=${this.submitting}>
                            ${this.submitting ? html`
                                <span class="spinner-border spinner-border-sm me-2" role="status"></span>
                                Saving...
                            ` : submitText}
                        </button>
                        <button 
                            type="button" 
                            class="btn btn-secondary"
                            @click=${this._handleCancel}
                            ?disabled=${this.submitting}>
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        `;
    }

    _formatDateForInput(dateString) {
        if (!dateString) {
            return new Date().toISOString().split('T')[0];
        }
        return dateString;
    }

    _handleSubmit(e) {
        e.preventDefault();

        const formData = new FormData(e.target);
        const studentData = {
            firstName: formData.get('firstName'),
            lastName: formData.get('lastName'),
            email: formData.get('email'),
            matriculationNumber: formData.get('matriculationNumber'),
            enrollmentDate: formData.get('enrollmentDate')
        };

        if (this.mode === 'edit' && this.student?.id) {
            studentData.id = this.student.id;
            this.dispatchEvent(new CustomEvent('update-student', {
                detail: { student: studentData },
                bubbles: true,
                composed: true
            }));
        } else {
            this.dispatchEvent(new CustomEvent('create-student', {
                detail: { student: studentData },
                bubbles: true,
                composed: true
            }));
        }
    }

    _handleCancel() {
        this.dispatchEvent(new CustomEvent('cancel-form', {
            bubbles: true,
            composed: true
        }));
    }

    show(student = null, mode = 'create') {
        this.student = student ? { ...student } : null;
        this.mode = mode;
        this.visible = true;
        this.submitting = false;
    }

    hide() {
        this.visible = false;
        this.student = null;
        this.submitting = false;
    }
}

customElements.define('student-form', StudentForm);
