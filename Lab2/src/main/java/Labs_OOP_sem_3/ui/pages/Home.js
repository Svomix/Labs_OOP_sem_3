import React, {Component} from "react";

class Home extends React.Component {
    state = {
        isDropdownOpen: false,
        selectedOption: 'Выберите функцию',
        isTabulatedModalOpen: false,
        isMathFunctionModalOpen: false,
        xFrom: '',
        xTo: '',
        points: '',
        xValues: [], // Массив для хранения значений x
        yValues: [], // Массив для хранения значений y
        currentX: '', // Текущее значение x
        currentY: '', // Текущее значение y
        errorMessage: '', // Сообщение об ошибке
        mathFunctionErrorMessage: '', // Сообщение об ошибке для MathFunction
    };

    handleTabulatedClick = () => {
        console.log('Tabulated button clicked');
        this.setState({isTabulatedModalOpen: true, errorMessage: ''}); // Открываем модальное окно для Tabulated и очищаем сообщение об ошибке
    };

    handleTabulatedModalClose = () => {
        this.setState({
            isTabulatedModalOpen: false,
            xValues: [],
            yValues: [],
            currentX: '',
            currentY: '',
            errorMessage: ''
        }); // Закрываем окно и очищаем значения
    };

    handleInputChange = (e, index, key) => {
        if (index !== undefined && key) {
            const {xValues, yValues} = this.state;
            const newValues = key === 'x' ? [...xValues] : [...yValues];
            newValues[index] = e.target.value;
            this.setState({
                [key === 'x' ? 'xValues' : 'yValues']: newValues
            });
        } else {
            this.setState({[e.target.name]: e.target.value}); // Обновляем текущее значение x или y
        }
    };

    handleAddValues = () => {
        const {currentX, currentY, xValues, yValues} = this.state;
        this.setState({
            xValues: [...xValues, currentX], // Добавляем новое значение x
            yValues: [...yValues, currentY], // Добавляем новое значение y
            currentX: '', // Очищаем текущее значение x
            currentY: '', // Очищаем текущее значение y
        });
    };

    handleDeleteValue = (index) => {
        const {xValues, yValues} = this.state;
        const newXValues = xValues.filter((_, i) => i !== index);
        const newYValues = yValues.filter((_, i) => i !== index);
        this.setState({xValues: newXValues, yValues: newYValues});
    };

    handleDeleteAllValues = () => {
        this.setState({xValues: [], yValues: []}); // Удаляем все значения x и y
    };

    handleMathFunctionHover = () => {
        this.setState({isDropdownOpen: true});
    };

    handleMathFunctionLeave = () => {
        this.setState({isDropdownOpen: false});
    };

    handleOptionClick = (option) => {
        this.setState({
            selectedOption: 'Выберите функцию',
            isDropdownOpen: false,
            isMathFunctionModalOpen: true,
            mathFunctionErrorMessage: ''
        });
    };

    handleMathFunctionModalClose = () => {
        this.setState({isMathFunctionModalOpen: false, mathFunctionErrorMessage: ''});
    };

    handleSubmitTabulated = () => {
        const {xValues, yValues} = this.state;
        let errorMessage = '';

        if (xValues.length !== yValues.length) {
            errorMessage = 'Количество значений X и Y должно совпадать.';
        } else {
            for (let i = 0; i < xValues.length; i++) {
                if (!xValues[i] || !yValues[i]) {
                    errorMessage = 'Заполните все точки значениями.';
                    break;
                }
            }
        }

        if (errorMessage) {
            this.setState({errorMessage});
            return;
        }

        console.log('Сохраненные значения:', xValues.map((x, index) => ({x, y: yValues[index]})));
        this.handleTabulatedModalClose();
    };

    handleSubmitMathFunction = () => {
        const {points, xFrom, xTo} = this.state;
        let mathFunctionErrorMessage = '';

        if (points < 0) {
            mathFunctionErrorMessage = 'Количество должно быть положительным.';
        }

        if (xFrom < 0 || xTo < 0) {
            mathFunctionErrorMessage = 'Значения x от и x до не могут быть отрицательными.';
        }

        if (mathFunctionErrorMessage) {
            this.setState({mathFunctionErrorMessage});
            return;
        }

        console.log(`xFrom: ${xFrom}, xTo: ${xTo}, Points: ${points}`);
        this.handleMathFunctionModalClose();
    };

    render() {
        const {
            isDropdownOpen,
            selectedOption,
            isTabulatedModalOpen,
            isMathFunctionModalOpen,
            xFrom,
            xTo,
            points,
            xValues,
            yValues,
            currentX,
            currentY,
            errorMessage,
            mathFunctionErrorMessage
        } = this.state;
        const options = ['Функция 1', 'Функция 2', 'Функция 3'];

        return (
            <div style={{textAlign: 'center', marginTop: '50px'}}>
                <h1>Вычисление функции</h1>
                <div>
                    <button onClick={this.handleTabulatedClick} style={buttonStyle}>
                        Tabulated
                    </button>
                    <div
                        style={{position: 'relative', display: 'inline-block'}}
                        onMouseEnter={this.handleMathFunctionHover}
                        onMouseLeave={this.handleMathFunctionLeave}
                    >
                        <button style={buttonStyle}>
                            {selectedOption === 'Выберите функцию' ? 'MathFunction' : selectedOption}
                        </button>
                        {isDropdownOpen && (
                            <div style={dropdownStyle}>
                                {options.map((option, index) => (
                                    <div
                                        key={index}
                                        style={optionStyle}
                                        onClick={() => this.handleOptionClick(option)}
                                        className="dropdown-option"
                                    >
                                        {option}
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>

                {isTabulatedModalOpen && (
                    <div style={modalOverlayStyle}>
                        <div style={modalStyle}>
                            <h2>Введите значения X и Y</h2>
                            {errorMessage && <div className="error-message">{errorMessage}</div>}
                            <div style={tableStyle}>
                                <div style={columnStyle}>
                                    <h3>X</h3>
                                    {xValues.map((x, index) => (
                                        <div key={index} className="value-row">
                                            <input
                                                type="text"
                                                name={`x-${index}`}
                                                value={x}
                                                onChange={(e) => this.handleInputChange(e, index, 'x')}
                                                placeholder="Введите X"
                                                className={!x ? 'error-input' : ''}
                                            />
                                            <button className="close-button"
                                                    onClick={() => this.handleDeleteValue(index)}>&times;</button>
                                        </div>
                                    ))}
                                    <input
                                        type="text"
                                        name="currentX"
                                        value={currentX}
                                        onChange={this.handleInputChange}
                                        placeholder="Введите X"
                                    />
                                </div>
                                <div style={columnStyle}>
                                    <h3>Y</h3>
                                    {yValues.map((y, index) => (
                                        <div key={index} className="value-row">
                                            <input
                                                type="text"
                                                name={`y-${index}`}
                                                value={y}
                                                onChange={(e) => this.handleInputChange(e, index, 'y')}
                                                placeholder="Введите Y"
                                                className={!y ? 'error-input' : ''}
                                            />
                                        </div>
                                    ))}
                                    <input
                                        type="text"
                                        name="currentY"
                                        value={currentY}
                                        onChange={this.handleInputChange}
                                        placeholder="Введите Y"
                                    />
                                </div>
                            </div>
                            <button onClick={this.handleAddValues} style={buttonStyle}>
                                Добавить
                            </button>
                            <button onClick={this.handleDeleteAllValues} style={buttonStyle}>
                                Удалить все
                            </button>
                            <button onClick={this.handleTabulatedModalClose} style={buttonStyle}>
                                Закрыть
                            </button>
                            <button onClick={this.handleSubmitTabulated} style={buttonStyle}>
                                Сохранить
                            </button>
                        </div>
                    </div>
                )}

                {isMathFunctionModalOpen && (
                    <div style={modalOverlayStyle}>
                        <div style={modalStyle}>
                            <h2>Введите параметры</h2>
                            {mathFunctionErrorMessage &&
                                <div className="error-message">{mathFunctionErrorMessage}</div>}
                            <label>
                                Количество точек разбиения:
                                <input
                                    type="number"
                                    name="points"
                                    value={points}
                                    onChange={this.handleInputChange}
                                />
                            </label>
                            <br/>
                            <label>
                                x от:
                                <input
                                    type="number"
                                    name="xFrom"
                                    value={xFrom}
                                    onChange={this.handleInputChange}
                                    min="0"
                                />
                            </label>
                            <br/>
                            <label>
                                x до:
                                <input
                                    type="number"
                                    name="xTo"
                                    value={xTo}
                                    onChange={this.handleInputChange}
                                    min="0"
                                />
                            </label>
                            <br/>
                            <button onClick={this.handleSubmitMathFunction} style={buttonStyle}>Подтвердить</button>
                            <button onClick={this.handleMathFunctionModalClose} style={buttonStyle}>Закрыть</button>
                        </div>
                    </div>
                )}
            </div>
        );
    }
}

const tableStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    marginTop: '20px',
};

const columnStyle = {
    width: '45%', // Ширина каждого столбца
    padding: '10px', // Отступы внутри столбца
    borderRight: '1px solid #ccc', // Граница между столбцами
    boxSizing: 'border-box', // Учитываем границы в ширине
};

const buttonStyle = {
    margin: '10px',
    padding: '15px 30px',
    fontSize: '16px',
    cursor: 'pointer',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#007BFF',
    color: 'white',
};

const dropdownStyle = {
    position: 'absolute',
    backgroundColor: '#007BFF',
    borderRadius: '10px',
    marginTop: '-10px',
    zIndex: 1,
    width: '225px',
};

const optionStyle = {
    padding: '10px',
    cursor: 'pointer',
    color: 'white',
};

const dropdownOptionHoverStyle = {
    backgroundColor: '#0056b3', // Цвет при наведении
};

const modalOverlayStyle = {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
};

const modalStyle = {
    background: 'white',
    padding: '20px',
    borderRadius: '5px',
    boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
};

export default Home;