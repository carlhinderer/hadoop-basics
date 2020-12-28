public class RegexExcludePathFilter implements PathFilter {
    private final String regex;

    public RegexExcludePathFilter (String regex) {
        this.regex = regex;
    }

    public boolean accept (Pathpath) {
        return!path.toString().matches(regex);
    }
}