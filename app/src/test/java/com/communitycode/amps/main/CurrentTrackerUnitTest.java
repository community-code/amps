package com.communitycode.amps.main;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CurrentTrackerUnitTest {
    @Test
    public void addHistory_overflow() throws Exception {
        BatteryInfoInterface mockBatteryInfo = mock(BatteryInfoInterface.class);
        CurrentTracker currentTracker = new CurrentTracker(null, mockBatteryInfo);

        for (int i = 0; i < CurrentTracker.MAX_HISTORY*2; i++) {
            currentTracker.addHistory(i);
        }

        assertEquals(CurrentTracker.MAX_HISTORY, currentTracker.currentHistory.size());

        assertEquals( "Check first in first out",
                CurrentTracker.MAX_HISTORY, currentTracker.currentHistory.get(0).intValue());
    }

    @Test
    public void updateAmpStatistics_emptyHistory() throws Exception {
        BatteryInfoInterface mockBatteryInfo = mock(BatteryInfoInterface.class);

        CurrentTracker currentTracker = new CurrentTracker(null, mockBatteryInfo);

        currentTracker.updateAmpStatistics();

        verify(mockBatteryInfo).setMinAmps(null);
        verify(mockBatteryInfo).setMaxAmps(null);
        verify(mockBatteryInfo).setCurrentAmps(null);
    }

    @Test
    public void updateAmpStatistics_hasHistory() throws Exception {
        BatteryInfoInterface mockBatteryInfo = mock(BatteryInfoInterface.class);

        CurrentTracker currentTracker = new CurrentTracker(null, mockBatteryInfo);
        currentTracker.addHistory(10);
        currentTracker.addHistory(-10);
        currentTracker.addHistory(5);
        currentTracker.addHistory(10);
        currentTracker.addHistory(-3);

        currentTracker.updateAmpStatistics();

        verify(mockBatteryInfo).setMinAmps(-10);
        verify(mockBatteryInfo).setMaxAmps(10);
        verify(mockBatteryInfo).setCurrentAmps(-3);
    }
}
