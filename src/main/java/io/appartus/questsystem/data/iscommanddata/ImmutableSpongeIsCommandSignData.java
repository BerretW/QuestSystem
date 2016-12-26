package io.appartus.questsystem.data.iscommanddata;

import io.appartus.questsystem.questsystem;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Optional;

public class ImmutableSpongeIsCommandSignData extends AbstractImmutableBooleanData<ImmutableIsCommandSignData, IsCommandSignData> implements ImmutableIsCommandSignData
{
	public ImmutableSpongeIsCommandSignData(boolean value)
	{
		super(value, questsystem.IS_COMMAND_SIGN, false);
	}

	@Override
	public ImmutableValue<Boolean> isCommandSign()
	{
		return getValueGetter();
	}

	@Override
	public <E> Optional<ImmutableIsCommandSignData> with(Key<? extends BaseValue<E>> key, E value)
	{
		if (this.supports(key))
		{
			return Optional.of(asMutable().set(key, value).asImmutable());
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public DataContainer toContainer()
	{
		return super.toContainer().set(questsystem.IS_COMMAND_SIGN.getQuery(), this.getValue());
	}

	@Override
	public int getContentVersion()
	{
		return 1;
	}

	@Override
	public IsCommandSignData asMutable()
	{
		return new SpongeIsCommandSignData(getValue());
	}

}
