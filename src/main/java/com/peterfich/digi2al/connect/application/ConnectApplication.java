package com.peterfich.digi2al.connect.application;

import com.peterfich.digi2al.connect.ConnectService;
import com.peterfich.digi2al.connect.id.IdGenerator;
import com.peterfich.digi2al.connect.id.SimpleIdGenerator;
import com.peterfich.digi2al.connect.repository.GameRepository;
import com.peterfich.digi2al.connect.repository.InMemoryGameRepository;
import com.peterfich.digi2al.connect.stateupdaters.DownDiagonalWin;
import com.peterfich.digi2al.connect.stateupdaters.Draw;
import com.peterfich.digi2al.connect.stateupdaters.HorizontalWin;
import com.peterfich.digi2al.connect.stateupdaters.StateUpdater;
import com.peterfich.digi2al.connect.stateupdaters.TurnUpdater;
import com.peterfich.digi2al.connect.stateupdaters.UpDiagonalWin;
import com.peterfich.digi2al.connect.stateupdaters.VerticalWin;
import com.peterfich.digi2al.connect.validators.move.ColourValidator;
import com.peterfich.digi2al.connect.validators.move.MoveValidator;
import com.peterfich.digi2al.connect.validators.move.TurnValidator;
import com.peterfich.digi2al.connect.validators.state.GameAllReadyADraw;
import com.peterfich.digi2al.connect.validators.state.GameAllReadyWon;
import com.peterfich.digi2al.connect.validators.state.StateValidator;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.List;

import static java.util.Arrays.asList;

public class ConnectApplication extends Application<ConnectConfiguration> {

    public static void main(String[] args) throws Exception {
        new ConnectApplication().run(args);
    }

    @Override
    public String getName() {
        return "Connect";
    }

    @Override
    public void run(ConnectConfiguration connectConfiguration, Environment environment) throws Exception {
        IdGenerator idGenerator = new SimpleIdGenerator();
        GameRepository repository = new InMemoryGameRepository();
        List<StateValidator> stateValidators = asList(new GameAllReadyADraw(), new GameAllReadyWon());
        List<MoveValidator> moveValidators = asList(new ColourValidator(), new TurnValidator());
        List<StateUpdater> stateUpdaters = asList(
                new HorizontalWin(4),
                new VerticalWin(4),
                new UpDiagonalWin(4),
                new DownDiagonalWin(4),
                new Draw(),
                new TurnUpdater());

        ConnectService service = new ConnectService(idGenerator, repository, stateValidators, moveValidators, stateUpdaters, 7, 6);

        environment.jersey().register(new Connect4Resource(service));
        environment.jersey().register(IllegalMoveExceptionMapper.class);
        environment.jersey().register(ColumnAllReadyFullExceptionMapper.class);
        environment.jersey().register(GameAllReadyEndedExceptionMapper.class);
        environment.jersey().register(IllegalColourExceptionMapper.class);

        environment.jersey().register(InvalidColumnExceptionMapper.class);
    }
}
