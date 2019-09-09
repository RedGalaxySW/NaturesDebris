/*
 * Copyright (c) 2019 RedGalaxy & contributors
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 8 - 26 - 2019
 */

package modernity.common.net.pkt;

import modernity.net.ESide;
import modernity.net.PacketChannel;

public class MDPackets {
    public static void register( PacketChannel channel ) {
        channel.register( ESide.SERVER, SPacketOpenContainer.class );
        channel.register( ESide.SERVER, SPacketStructure.class );
        channel.register( ESide.SERVER, SPacketSettingsInit.class );
        channel.register( ESide.SERVER, SPacketSettingChange.class );
        channel.register( ESide.SERVER, SPacketSeed.class );
    }
}
