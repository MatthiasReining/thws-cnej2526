/**
 * Main entry point for the Student Management Application
 * Imports all necessary components and initializes the application
 */

import './App.js';
import './StudentList.js';
import './StudentForm.js';

// Application is ready when all components are loaded
console.log('Student Management Application initialized');

// Export service for potential external use
export { StudentService } from './StudentService.js';
