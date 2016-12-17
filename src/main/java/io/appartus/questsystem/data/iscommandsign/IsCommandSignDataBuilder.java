package io.appartus.questsystem.data.iscommandsign;

import io.appartus.questsystem.questsystem;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;

import java.util.Optional;

public class IsCommandSignDataBuilder implements DataManipulatorBuilder<IsCommandSignData, ImmutableIsCommandSignData>
{
	@Override
	public IsCommandSignData create()
	{
		return new SpongeIsCommandSignData();
	}

	@Override
	public Optional<IsCommandSignData> createFrom(DataHolder dataHolder)
	{
		return create().fill(dataHolder);
	}

	@Override
	public Optional<IsCommandSignData> build(DataView container)
	{
		if (!container.contains(questsystem.IS_COMMAND_SIGN.getQuery()))
		{
			return Optional.empty();
		}

		IsCommandSignData data = new SpongeIsCommandSignData((Boolean) container.get(questsystem.IS_COMMAND_SIGN.getQuery()).get());
        return Optional.of(data);
	}
}
