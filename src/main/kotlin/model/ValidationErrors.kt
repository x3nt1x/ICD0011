package model

data class ValidationErrors(var errors: MutableList<ValidationError> = ArrayList()) {
    fun addError(error: String) {
        errors.add(ValidationError(error))
    }
}