/**
 * StudentService - Service class for handling API communication
 * Provides methods for CRUD operations on students via fetch API
 */
export class StudentService {
    constructor(baseUrl = '/students') {
        this.baseUrl = baseUrl;
    }

    /**
     * Fetch all students from the backend
     * @returns {Promise<Array>} Array of student objects
     */
    async getAllStudents() {
        try {
            const response = await fetch(this.baseUrl, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching students:', error);
            throw error;
        }
    }

    /**
     * Fetch a single student by ID
     * @param {number} id - Student ID
     * @returns {Promise<Object>} Student object
     */
    async getStudentById(id) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error(`Error fetching student ${id}:`, error);
            throw error;
        }
    }

    /**
     * Create a new student
     * @param {Object} studentData - Student data without ID
     * @returns {Promise<Object>} Created student object
     */
    async createStudent(studentData) {
        try {
            const response = await fetch(this.baseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(studentData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error creating student:', error);
            throw error;
        }
    }

    /**
     * Update an existing student
     * @param {number} id - Student ID
     * @param {Object} studentData - Updated student data
     * @returns {Promise<Object>} Updated student object
     */
    async updateStudent(id, studentData) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(studentData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
            }

            return await response.json();
        } catch (error) {
            console.error(`Error updating student ${id}:`, error);
            throw error;
        }
    }

    /**
     * Delete a student
     * @param {number} id - Student ID
     * @returns {Promise<void>}
     */
    async deleteStudent(id) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}`, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            // DELETE returns void, no content to parse
            return;
        } catch (error) {
            console.error(`Error deleting student ${id}:`, error);
            throw error;
        }
    }
}
