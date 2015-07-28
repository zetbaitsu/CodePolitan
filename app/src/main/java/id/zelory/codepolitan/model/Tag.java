package id.zelory.codepolitan.model;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class Tag
{
    private String name;
    private String slug;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSlug()
    {
        return slug;
    }

    public void setSlug(String slug)
    {
        this.slug = slug;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
