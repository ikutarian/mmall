package com.ikutarian.mmall.form;

/**
 * 更新用户信息的表单
 *
 * 注意：username不能被更新
 */
public class UpdateUserInfoForm {

    private String email;
    private String phone;
    private String question;
    private String answer;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
