# TODO: Add Additional Fields for User Registration

## Backend Updates
- [x] Update User.java model to include additional fields: fullName, primarySkill, experience for NORMAL users; companyName, recruiterName, address for EMPLOYEE users.
- [x] Update UserController.java: Update SignupRequest to include new fields. Update signup method to set fields based on role.
- [x] Update SignupRequest class to include fullName, primarySkill, experience, companyName, recruiterName, address.

## Frontend Updates
- [x] Update LoginSignup.tsx: Add form fields for NORMAL users: fullName, primarySkill, experience. For EMPLOYEE users: companyName, recruiterName, address.
- [x] Update form state to include new fields.
- [x] Update form submission to send new fields to backend.

## Testing
- [ ] Test registration and login for both NORMAL and EMPLOYEE roles with new fields.
- [ ] Ensure backend validation and frontend error handling.
