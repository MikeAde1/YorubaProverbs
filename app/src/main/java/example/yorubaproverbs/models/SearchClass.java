package example.yorubaproverbs.models;

public class SearchClass {


    public SearchClass(){

    }
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public String getTranslation() {
        return Translation;
    }

    public void setTranslation(String usage) {
        Translation = usage;
    }

    String Content, Context, Explanation, Translation;
}
