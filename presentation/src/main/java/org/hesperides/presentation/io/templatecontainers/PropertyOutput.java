package org.hesperides.presentation.io.templatecontainers;

import com.google.gson.annotations.SerializedName;
import lombok.Value;
import org.hesperides.domain.templatecontainers.queries.AbstractPropertyView;
import org.hesperides.domain.templatecontainers.queries.IterablePropertyView;
import org.hesperides.domain.templatecontainers.queries.PropertyView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Value
public class PropertyOutput implements Comparable<PropertyOutput>{

    String name;
    @SerializedName("required")
    boolean isRequired;
    String comment;
    String defaultValue;
    String pattern;
    @SerializedName("password")
    boolean isPassword;
    @SerializedName("fields")
    List<PropertyOutput> properties;

    public static PropertyOutput fromPropertyView(PropertyView propertyView) {
        return new PropertyOutput(
                propertyView.getName(),
                propertyView.isRequired(),
                propertyView.getComment(),
                propertyView.getDefaultValue(),
                propertyView.getPattern(),
                propertyView.isPassword(),
                null
        );
    }

    public static PropertyOutput fromIterablePropertyView(IterablePropertyView iterablePropertyView) {
        return new PropertyOutput(
                iterablePropertyView.getName(),
                false,
                "",
                "",
                "",
                false,
                PropertyOutput.fromAbstractPropertyViews(iterablePropertyView.getProperties())
        );
    }

    private static List<PropertyOutput> fromAbstractPropertyViews(List<AbstractPropertyView> abstractPropertyViews) {
        List<PropertyOutput> propertyOutputs = new ArrayList<>();

        if (abstractPropertyViews != null) {
            for (AbstractPropertyView abstractPropertyView : abstractPropertyViews) {
                PropertyOutput propertyOutput = fromAbstractPropertyView(abstractPropertyView);
                if (propertyOutput != null) {
                    propertyOutputs.add(propertyOutput);
                }
            }
        }

        return propertyOutputs;
    }

    public static PropertyOutput fromAbstractPropertyView(AbstractPropertyView abstractPropertyView) {
        PropertyOutput propertyOutput = null;
        if (abstractPropertyView instanceof PropertyView) {
            PropertyView propertyView = (PropertyView) abstractPropertyView;
            propertyOutput = PropertyOutput.fromPropertyView(propertyView);
        } else if (abstractPropertyView instanceof IterablePropertyView) {
            IterablePropertyView iterablePropertyView = (IterablePropertyView) abstractPropertyView;
            propertyOutput = PropertyOutput.fromIterablePropertyView(iterablePropertyView);
        }
        return propertyOutput;
    }
    @Override
    public int compareTo(@NotNull PropertyOutput o) {
        return this.name.compareTo(o.name);
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PropertyOutput)) {
            return false;
        } else {
            PropertyOutput other;
            label76:
            {
                other = (PropertyOutput) o;
                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name == null) {
                        break label76;
                    }
                } else if (this$name.equals(other$name)) {
                    break label76;
                }

                return false;
            }

            if (this.isRequired() != other.isRequired()) {
                return false;
            } else {
                String this$comment = this.getComment();
                String other$comment = (other.getComment() == null) ? "" : other.getComment();
                if (this$comment == null) {
                    if (other$comment != null) {
                        return false;
                    }
                } else if (!this$comment.equals(other$comment)) {
                    return false;
                }

                Object this$defaultValue = this.getDefaultValue();
                Object other$defaultValue = other.getDefaultValue();
                if (this$defaultValue == null) {
                    if (other$defaultValue != null) {
                        return false;
                    }
                } else if (!this$defaultValue.equals(other$defaultValue)) {
                    return false;
                }

                label54:
                {
                    Object this$pattern = this.getPattern();
                    Object other$pattern = other.getPattern();
                    if (this$pattern == null) {
                        if (other$pattern == null) {
                            break label54;
                        }
                    } else if (this$pattern.equals(other$pattern)) {
                        break label54;
                    }

                    return false;
                }

                if (this.isPassword() != other.isPassword()) {
                    return false;
                } else {
                    Object this$properties = this.getProperties();
                    Object other$properties = other.getProperties();
                    if (this$properties == null) {
                        if (other$properties != null) {
                            return false;
                        }
                    } else if (!this$properties.equals(other$properties)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }
}
